package com.rubber.at.tennis.map.service.apply;

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
import com.rubber.base.components.util.result.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    private UserCollectCourtService userCollectMapService;

    private static final String PREFIX = "TCM";


    /**
     * 上报地图
     *
     * @param applyModel 请求
     * @return 返回是否成功
     */
    @Override
    public ResultMsg reportCourt(CourtMapApplyDto applyModel) {
        TennisCourtMapEntity courtMap = new TennisCourtMapEntity();
        if (StrUtil.isNotEmpty(applyModel.getCourtCode())){
            LambdaQueryWrapper<TennisCourtMapEntity> lqw = new LambdaQueryWrapper<>();
            lqw.eq(TennisCourtMapEntity::getCourtCode,applyModel.getCourtCode());
            courtMap =  iTennisCourtMapDal.getOne(lqw);
            BeanUtils.copyProperties(applyModel,courtMap,"courtCode");
            courtMap.setStatus(CourtMapStatusEnums.ON.getStatus());
            courtMap.setUpdateTime(new Date());
            if (applyModel.getReserveInfo() != null){
                courtMap.setReserveInfo(applyModel.getReserveInfo().toJSONString());
            }
            iTennisCourtMapDal.updateById(courtMap);
        }else {
            BeanUtils.copyProperties(applyModel,courtMap);
            courtMap.setCourtCode(PREFIX + IdUtil.nanoId(16));
            courtMap.setStatus(CourtMapStatusEnums.ON.getStatus());
            courtMap.setReporter(String.valueOf(applyModel.getUid()));
            courtMap.setCreateTime(new Date());
            courtMap.setUpdateTime(new Date());
            if (applyModel.getReserveInfo() != null){
                courtMap.setReserveInfo(applyModel.getReserveInfo().toJSONString());
            }
            iTennisCourtMapDal.save(courtMap);
        }
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
