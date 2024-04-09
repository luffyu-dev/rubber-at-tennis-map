package com.rubber.at.tennis.map.service.apply;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.map.api.TennisCourtMapApplyApi;
import com.rubber.at.tennis.map.api.dto.CourtMapApplyDto;
import com.rubber.at.tennis.map.api.dto.TennisCourtMapDto;
import com.rubber.at.tennis.map.api.enums.CourtMapStatusEnums;
import com.rubber.at.tennis.map.api.request.RegionCodeRequest;
import com.rubber.at.tennis.map.dao.dal.ITennisCourtMapDal;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.rubber.at.tennis.map.service.query.TennisCourtMapQueryService;
import com.rubber.base.components.util.result.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Slf4j
@Service("tennisCourtMapApplyApi")
public class TennisCourtMapApplyService implements TennisCourtMapApplyApi {

    @Autowired
    private ITennisCourtMapDal iTennisCourtMapDal;

    @Autowired
    private TennisCourtMapQueryService tennisCourtMapQueryService;

    @Autowired
    private UserCollectCourtService userCollectMapService;

    private static final String PREFIX = "TCM";

    // 后面需要迁移到库表中
    private static List<Integer> MANAGER = new ArrayList<>(Arrays.asList(1000001));

    /**
     * 上报地图
     *
     * @param applyModel 请求
     * @return 返回是否成功
     */
    @Override
    public ResultMsg reportCourt(CourtMapApplyDto applyModel) {
        LambdaQueryWrapper<TennisCourtMapEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(TennisCourtMapEntity::getCourtCode,applyModel.getCourtCode());
        TennisCourtMapEntity courtMap =  iTennisCourtMapDal.getOne(lqw);
        if (courtMap != null){
            if (!MANAGER.contains(applyModel.getUid()) && !applyModel.getUid().toString().equals(courtMap.getReporter())){
                return ResultMsg.error("没有权限操作");
            }
            BeanUtils.copyProperties(applyModel,courtMap,"courtCode");
            courtMap.setStatus(CourtMapStatusEnums.ON.getStatus());
            courtMap.setUpdateTime(new Date());
            if (CollUtil.isNotEmpty(applyModel.getCourtTagList())){
                courtMap.setCourtTag(CollUtil.join(applyModel.getCourtTagList(),","));
            }
            if (applyModel.getReserveInfo() != null){
                courtMap.setReserveInfo(applyModel.getReserveInfo().toJSONString());
            }
            if (applyModel.getFeeInfo() != null){
                courtMap.setFeeInfo(applyModel.getFeeInfo().toJSONString());
            }
            iTennisCourtMapDal.updateById(courtMap);
        }else {
            courtMap = new TennisCourtMapEntity();
            BeanUtils.copyProperties(applyModel,courtMap);
            if (CollUtil.isNotEmpty(applyModel.getCourtTagList())){
                courtMap.setCourtTag(CollUtil.join(applyModel.getCourtTagList(),","));
            }
            courtMap.setCourtCode(PREFIX + IdUtil.nanoId(16));
            courtMap.setStatus(CourtMapStatusEnums.ON.getStatus());
            courtMap.setReporter(String.valueOf(applyModel.getUid()));
            courtMap.setCreateTime(new Date());
            courtMap.setUpdateTime(new Date());
            if (applyModel.getReserveInfo() != null){
                courtMap.setReserveInfo(applyModel.getReserveInfo().toJSONString());
            }
            if (applyModel.getFeeInfo() != null){
                courtMap.setFeeInfo(applyModel.getFeeInfo().toJSONString());
            }
            iTennisCourtMapDal.save(courtMap);
        }
        // 清除缓存
        tennisCourtMapQueryService.clearQueryCache(courtMap.getCity());

        TennisCourtMapDto dto = new TennisCourtMapDto();
        dto.setCourtCode(courtMap.getCourtCode());
        return ResultMsg.success(dto);
    }

    /**
     * 关注球场
     *
     * @param request 当前的请求
     * @return 返回是否成功
     */
    @Override
    public ResultMsg collectCourt(RegionCodeRequest request) {
        userCollectMapService.collectCourt(request);
        return ResultMsg.success();
    }

    /**
     * 取消关注
     *
     * @param request 当前的请求
     * @return 返回是否成功
     */
    @Override
    public ResultMsg unCollectCourt(RegionCodeRequest request) {
        userCollectMapService.unCollectCourt(request);
        return ResultMsg.success();
    }

}
