package com.rubber.at.tennis.map.service.query;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.map.api.TennisCourtMapQueryApi;
import com.rubber.at.tennis.map.api.dto.TennisCourtMapDto;
import com.rubber.at.tennis.map.api.enums.CourtMapStatusEnums;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.at.tennis.map.dao.condition.UserTennisCourtCondition;
import com.rubber.at.tennis.map.dao.dal.ITennisCourtMapDal;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.rubber.at.tennis.map.dao.entity.UserCollectCourtEntity;
import com.rubber.at.tennis.map.service.apply.UserCollectCourtService;
import com.rubber.base.components.mysql.utils.PageUtils;
import com.rubber.base.components.util.result.page.BaseRequestPage;
import com.rubber.base.components.util.result.page.ResultPage;
import lombok.extern.slf4j.Slf4j;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Slf4j
@Service("tennisCourtMapQueryApi")
public class TennisCourtMapQueryService implements TennisCourtMapQueryApi {

    @Resource
    private ITennisCourtMapDal iTennisCourtMapDal;

    @Autowired
    private UserCollectCourtService userCollectMapService;

    /**
     * 通过地区搜索附近的地图
     *
     * @param queryModel 当前的请求参数
     * @return 球场的基本信息
     */
    @Override
    public ResultPage<TennisCourtMapDto> searchByRegion(RegionQueryRequest queryModel) {
        if (queryModel.isJustCollect()){
            return queryUserCollectCourt(queryModel);
        }
        return searchByRegionValue(queryModel);
    }

    /**
     * 搜索查询
     */
    public ResultPage<TennisCourtMapDto> searchByRegionValue(RegionQueryRequest queryModel) {
        Page<TennisCourtMapEntity> page = new Page<>();
        page.setCurrent(queryModel.getPage());
        page.setSize(queryModel.getSize());
        page.setSearchCount(false);

        // 查询分页数据
        LambdaQueryWrapper<TennisCourtMapEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(TennisCourtMapEntity::getProvince,queryModel.getProvince())
                .eq(TennisCourtMapEntity::getCity,queryModel.getCity())
                .eq(TennisCourtMapEntity::getStatus, CourtMapStatusEnums.ON.getStatus());
        if (StrUtil.isNotEmpty(queryModel.getCourtName())){
            lqw.like(TennisCourtMapEntity::getCourtName,queryModel.getCourtName());
        }
        iTennisCourtMapDal.page(page,lqw);

        // 查询已经全部的球场信息
        List<String> collectedCourt = new ArrayList<>();
        if (queryModel.getUid() != null){
            collectedCourt = userCollectMapService.queryUserCollectedCourt(queryModel);
        }
        List<TennisCourtMapDto> list =  handlerMapResult(queryModel,page.getRecords(),collectedCourt);
        return PageUtils.convertPageResult(list,page);

    }

    /**
     * 查询用户关注的球场
     */
    public ResultPage<TennisCourtMapDto> queryUserCollectCourt(RegionQueryRequest queryModel) {
        Page<TennisCourtMapEntity> page = new Page<>();
        page.setCurrent(queryModel.getPage());
        page.setSize(queryModel.getSize());
        page.setSearchCount(false);

        UserTennisCourtCondition condition = new UserTennisCourtCondition();
        condition.setUid(queryModel.getUid());
        condition.setCourtName(queryModel.getCourtName());
        iTennisCourtMapDal.queryCollectPage(page,condition);

        if (CollUtil.isEmpty(page.getRecords())){
            return new ResultPage<>();
        }
        List<TennisCourtMapDto> list =  page.getRecords().stream().map(i->{
            TennisCourtMapDto courtMapDto = convertToDto(i,queryModel);
            courtMapDto.setCollected(true);
            return courtMapDto;
        }).collect(Collectors.toList());
        return PageUtils.convertPageResult(list,page);
    }








    /**
     * 查询某个地图详情
     *
     * @param request 具体的查询code
     * @return 球场的基本信息
     */
    @Override
    public TennisCourtMapDto getByCode(RegionQueryRequest request) {
        LambdaQueryWrapper<TennisCourtMapEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(TennisCourtMapEntity::getCourtCode,request.getCourtCode());
        TennisCourtMapEntity data =  iTennisCourtMapDal.getOne(lqw);
        if (data == null){
            return null;
        }
        TennisCourtMapDto dto = convertToDto(data,request);
        if (request.getUid() != null){
            UserCollectCourtEntity userCollect = userCollectMapService.getUserCollectInfo(request, request.getCourtCode());
            dto.setCollected(userCollect != null && userCollect.getStatus() == 1);
        }
        return dto;
    }


    /**
     * 数据转换
     * @param queryModel
     * @param mapList
     * @return
     */
    private List<TennisCourtMapDto>  handlerMapResult(RegionQueryRequest queryModel,List<TennisCourtMapEntity> mapList ,List<String> collectedCourt){
        if (CollUtil.isEmpty(mapList)){
            return new ArrayList<>();
        }
        return mapList.stream().map(i->convertToDto(i,queryModel,collectedCourt))
                .sorted(Comparator.comparing(TennisCourtMapDto::getLbsDistance))
                .collect(Collectors.toList());
    }



    private TennisCourtMapDto convertToDto(TennisCourtMapEntity courtMapEntity,RegionQueryRequest queryModel,List<String> collectedCourt){
        TennisCourtMapDto dto = convertToDto(courtMapEntity,queryModel);
        if (CollUtil.isNotEmpty(collectedCourt) && collectedCourt.contains(dto.getCourtCode())){
            dto.setCollected(true);
        }
        return dto;
    }

    private TennisCourtMapDto convertToDto(TennisCourtMapEntity courtMapEntity,RegionQueryRequest queryModel){
        TennisCourtMapDto dto = new TennisCourtMapDto();
        BeanUtils.copyProperties(courtMapEntity,dto);
        if (queryModel != null && StrUtil.isNotEmpty(queryModel.getLatitude()) && StrUtil.isNotEmpty(queryModel.getLongitude())){
            getDistance(queryModel,dto);
        }
        return dto;
    }



    /**
     * 计算两者直接的距离
     */
    private void getDistance(RegionQueryRequest queryModel,TennisCourtMapDto courtMap){
        //给定两个坐标系,计算两点相差距离
        GlobalCoordinates source = new GlobalCoordinates(Double.parseDouble(queryModel.getLatitude()), Double.parseDouble(queryModel.getLongitude()));
        GlobalCoordinates target = new GlobalCoordinates(Double.parseDouble(courtMap.getLatitude()), Double.parseDouble(courtMap.getLongitude()));
        //Sphere坐标的计算结果
        double meter1 =getDistanceMeter(source,target, Ellipsoid.Sphere);
        //WGS84坐标系计算结果
        //double meter2 = getDistanceMeter(source,target,Ellipsoid.WGS84);
        courtMap.setLbsDistance((int)meter1);
    }

    private double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){
        //创建GeodeticCalculator,调用计算方法,传入坐标系,经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }
}
