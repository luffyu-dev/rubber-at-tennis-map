package com.rubber.at.tennis.map.service.query;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
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
import com.rubber.base.components.util.LbsUtils;
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
import org.springframework.util.StringUtils;

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
     * 地图的缓存
     * 60分钟
     */
    private FIFOCache<String,List<TennisCourtMapEntity>> courtCache = CacheUtil.newFIFOCache(10,60 * 60 * 1000);


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
     * 最大支持300条数据搜索
     */
    public ResultPage<TennisCourtMapDto> searchByRegionValue(RegionQueryRequest queryModel) {
        String cacheKey = "COURT:" + queryModel.getCity();
        boolean isNeedCache = StrUtil.isNotEmpty(queryModel.getCourtName());
        List<TennisCourtMapEntity> dbList = null;
        if (isNeedCache){
            dbList = courtCache.get(cacheKey);
        }
        Page<TennisCourtMapEntity> page = new Page<>();
        page.setCurrent(queryModel.getPage());
        page.setSize(300);
        page.setSearchCount(false);
        if (CollUtil.isEmpty(dbList)) {
            // 查询分页数据
            LambdaQueryWrapper<TennisCourtMapEntity> lqw = new LambdaQueryWrapper<>();
            lqw.eq(TennisCourtMapEntity::getProvince, queryModel.getProvince())
                    .eq(TennisCourtMapEntity::getCity, queryModel.getCity())
                    .eq(TennisCourtMapEntity::getStatus, CourtMapStatusEnums.ON.getStatus());

            iTennisCourtMapDal.page(page, lqw);
            dbList = page.getRecords();
            if (CollUtil.isNotEmpty(dbList)){
                courtCache.put(cacheKey,dbList);
            }
        }
        // 名称过滤
        if (CollUtil.isNotEmpty(dbList) && StrUtil.isNotEmpty(queryModel.getCourtName())) {
            dbList = dbList.stream().filter(i->i.getCourtName().contains(queryModel.getCourtName())).collect(Collectors.toList());
        }
        // 数据是空的直接返回
        if (CollUtil.isEmpty(dbList)){
            return PageUtils.convertPageResult(new ArrayList<>(),page);
        }
        // 查询已经全部的球场信息
        List<String> collectedCourt = new ArrayList<>();
        if (queryModel.getUid() != null) {
            collectedCourt = userCollectMapService.queryUserCollectedCourt(queryModel);
        }
        List<TennisCourtMapDto> resultList = handlerMapResult(queryModel, dbList, collectedCourt);
        // 后面需要删除
        if (queryModel.getSize() <= 10 && resultList.size() > queryModel.getSize()){
            resultList = resultList.subList(0,queryModel.getSize());
        }
        return PageUtils.convertPageResult(resultList,page);

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
        //计算距离
        getDistance(request,dto);
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
        if (StrUtil.isNotEmpty(courtMapEntity.getReserveInfo())){
            dto.setReserveInfo(JSON.parseObject(courtMapEntity.getReserveInfo()));
        }
        return dto;
    }


    /**
     * 计算两者直接的距离
     */
    private void getDistance(RegionQueryRequest queryModel,TennisCourtMapDto courtMap){
        if (StrUtil.isEmpty(queryModel.getLatitude()) || StrUtil.isEmpty(queryModel.getLongitude())){
            return;
        }
        double meter1 = LbsUtils.getDistance(queryModel.getLatitude(),queryModel.getLongitude(),courtMap.getLatitude(),courtMap.getLongitude());
        courtMap.setLbsDistance((int)meter1);
    }


    /**
     * 清空缓存
     * @param city
     */
    public void clearQueryCache(String city){
        String cacheKey = "COURT:" + city;
        courtCache.remove(cacheKey);
    }
}
