package com.rubber.at.tennis.map.api;

import com.rubber.at.tennis.map.api.dto.TennisCourtMapDto;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.base.components.util.result.page.ResultPage;

import java.util.List;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
public interface TennisCourtMapQueryApi {


    /**
     * 通过地区搜索附近的地图
     * @param queryModel 当前的请求参数
     * @return 球场的基本信息
     */
    ResultPage<TennisCourtMapDto> searchByRegion(RegionQueryRequest queryModel);


    /**
     * 查询某个地图详情
     * @param request 具体的查询code
     * @return 球场的基本信息
     */
    TennisCourtMapDto getByCode(RegionQueryRequest request);
}
