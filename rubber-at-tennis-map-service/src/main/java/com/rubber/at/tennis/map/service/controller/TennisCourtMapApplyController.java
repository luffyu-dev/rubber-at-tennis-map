package com.rubber.at.tennis.map.service.controller;

import com.rubber.at.tennis.map.api.TennisCourtMapApplyApi;
import com.rubber.at.tennis.map.api.TennisCourtMapQueryApi;
import com.rubber.at.tennis.map.api.dto.CourtMapApplyDto;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.at.tennis.map.service.apply.TennisCourtMapApplyService;
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
    @PostMapping("/submit/report")
    public ResultMsg search(@RequestBody CourtMapApplyDto dto){
        return tennisCourtMapApplyApi.reportMap(dto);
    }



}
