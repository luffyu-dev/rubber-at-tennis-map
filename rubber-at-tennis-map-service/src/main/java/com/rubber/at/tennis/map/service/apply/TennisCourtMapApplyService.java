package com.rubber.at.tennis.map.service.apply;

import cn.hutool.core.util.IdUtil;
import com.rubber.at.tennis.map.api.TennisCourtMapApplyApi;
import com.rubber.at.tennis.map.api.dto.CourtMapApplyDto;
import com.rubber.at.tennis.map.api.enums.CourtMapStatusEnums;
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

    private static final String PREFIX = "TCM";


    /**
     * 上报地图
     *
     * @param applyModel 请求
     * @return 返回是否成功
     */
    @Override
    public ResultMsg reportMap(CourtMapApplyDto applyModel) {
        TennisCourtMapEntity courtMap = new TennisCourtMapEntity();
        BeanUtils.copyProperties(applyModel,courtMap);
        courtMap.setCourtCode(PREFIX + IdUtil.nanoId(16));
        courtMap.setStatus(CourtMapStatusEnums.APPROVEING.getStatus());
        courtMap.setReporter(String.valueOf(applyModel.getUid()));
        courtMap.setCreateTime(new Date());
        courtMap.setUpdateTime(new Date());
        iTennisCourtMapDal.save(courtMap);
        return ResultMsg.success();
    }


}
