package com.rubber.at.tennis.map.service.controller;

import com.rubber.at.tennis.map.api.TennisCourtMapApplyApi;
import com.rubber.at.tennis.map.api.dto.CourtMapApplyDto;
import com.rubber.at.tennis.map.api.request.RegionCodeRequest;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Component
@RestController
@RequestMapping("/tennis-court")
public class TennisCourtMapApplyController {


    @Resource
    private TennisCourtMapApplyApi tennisCourtMapApplyApi;


    /**
     * 查询距离
     */
    @NeedLogin
    @PostMapping("/submit/report")
    public ResultMsg reportCourt(@RequestBody CourtMapApplyDto dto){
        return tennisCourtMapApplyApi.reportCourt(dto);
    }




    /**
     * 收藏球场
     */
    @NeedLogin
    @PostMapping("/collect")
    public ResultMsg collect(@RequestBody RegionCodeRequest request){
        return tennisCourtMapApplyApi.collectCourt(request);
    }


    /**
     * 取消收藏球场
     */
    @NeedLogin
    @PostMapping("/uncollect")
    public ResultMsg uncollect(@RequestBody RegionCodeRequest request){
        return tennisCourtMapApplyApi.unCollectCourt(request);
    }



}
