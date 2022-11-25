package com.rubber.at.tennis.map.api;

import com.rubber.at.tennis.map.api.dto.CourtMapApplyDto;
import com.rubber.at.tennis.map.api.request.RegionCodeRequest;
import com.rubber.base.components.util.result.ResultMsg;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
public interface TennisCourtMapApplyApi {

    /**
     * 上报地图
     * @param applyModel 请求
     * @return 返回是否成功
     */
    ResultMsg reportCourt(CourtMapApplyDto applyModel);


    /**
     * 关注球场
     * @param request 当前的请求
     * @return 返回是否成功
     */
    ResultMsg collectCourt(RegionCodeRequest request);



    /**
     * 取消关注
     * @param request 当前的请求
     * @return 返回是否成功
     */
    ResultMsg unCollectCourt(RegionCodeRequest request);

}
