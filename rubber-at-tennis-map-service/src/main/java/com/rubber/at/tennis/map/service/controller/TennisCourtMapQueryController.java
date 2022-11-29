package com.rubber.at.tennis.map.service.controller;

import com.rubber.at.tennis.map.api.TennisCourtMapQueryApi;
import com.rubber.at.tennis.map.api.dto.TennisCourtMapDto;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.ResultMsg;
import com.rubber.base.components.util.result.page.ResultPage;
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
     * 如果用户登录了，就可以拿到用户的信息
     */
    @PostMapping("/region/search")
    @NeedLogin(request = false)
    public ResultMsg search(@RequestBody RegionQueryRequest queryModel){
        return ResultMsg.success(tennisCourtMapQueryApi.searchByRegion(queryModel));
    }


    /**
     * 查询某个位置
     */
    @PostMapping("/info")
    @NeedLogin(request = false)
    public ResultMsg getByCode(@RequestBody RegionQueryRequest queryModel){
        return ResultMsg.success(tennisCourtMapQueryApi.getByCode(queryModel));
    }
}
