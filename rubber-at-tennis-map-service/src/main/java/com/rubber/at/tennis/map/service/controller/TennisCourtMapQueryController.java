package com.rubber.at.tennis.map.service.controller;

import com.rubber.at.tennis.map.api.TennisCourtMapQueryApi;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.base.components.util.result.ResultMsg;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Component
@RestController
@RequestMapping("/tennis-court")
public class TennisCourtMapQueryController {


    @Resource
    private TennisCourtMapQueryApi tennisCourtMapQueryApi;


    /**
     * 查询距离
     */
    @PostMapping("/region/search")
    public ResultMsg search(@RequestBody RegionQueryRequest queryModel){
        return ResultMsg.success(tennisCourtMapQueryApi.searchByRegion(queryModel));
    }


    /**
     * 查询某个位置
     */
    @PostMapping("/info")
    public ResultMsg getByCode(@RequestBody RegionQueryRequest queryModel){
        return ResultMsg.success(tennisCourtMapQueryApi.getByCode(queryModel));
    }
}
