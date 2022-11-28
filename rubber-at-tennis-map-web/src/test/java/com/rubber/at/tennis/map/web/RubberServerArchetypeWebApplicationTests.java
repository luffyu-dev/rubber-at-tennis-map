package com.rubber.at.tennis.map.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.map.api.TennisCourtMapApplyApi;
import com.rubber.at.tennis.map.api.TennisCourtMapQueryApi;
import com.rubber.at.tennis.map.api.dto.TennisCourtMapDto;
import com.rubber.at.tennis.map.api.request.RegionCodeRequest;
import com.rubber.at.tennis.map.api.request.RegionQueryRequest;
import com.rubber.at.tennis.map.dao.condition.UserTennisCourtCondition;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.rubber.at.tennis.map.dao.mapper.TennisCourtMapMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
@ComponentScan("com.rubber.at.tennis.*")
@MapperScan("com.rubber.at.tennis.**.dao.mapper")
public class RubberServerArchetypeWebApplicationTests {

    @Resource
    private TennisCourtMapApplyApi tennisCourtMapApplyApi;

    @Resource
    private TennisCourtMapQueryApi tennisCourtMapQueryApi;

    @Test
    public void doTest(){
        RegionCodeRequest codeRequest = new RegionCodeRequest();
        codeRequest.setUid(100000);
        codeRequest.setCourtCode("TCMcfbdcd08452c4242b2c1dd29b897718d");
        tennisCourtMapApplyApi.collectCourt(codeRequest);

        RegionQueryRequest request = new RegionQueryRequest();
        request.setUid(100000);
        request.setProvince("广东省");
        request.setCity("深圳市");
        request.setCourtCode("TCMcfbdcd08452c4242b2c1dd29b897718d");

        List<TennisCourtMapDto> tennisCourtMapDtos = tennisCourtMapQueryApi.searchByRegion(request);
        TennisCourtMapDto byCode = tennisCourtMapQueryApi.getByCode(request);


        tennisCourtMapApplyApi.unCollectCourt(codeRequest);

        List<TennisCourtMapDto> tennisCourtMapDtos2 = tennisCourtMapQueryApi.searchByRegion(request);

        TennisCourtMapDto byCode2 = tennisCourtMapQueryApi.getByCode(request);

    }


    @Autowired
    private TennisCourtMapMapper tennisCourtMapMapper;

    @Test
    public void doTest2(){
        Page<TennisCourtMapEntity> p = new Page<>();
        p.setCurrent(1);
        p.setSize(2);
        p.setSearchCount(false);

        UserTennisCourtCondition courtCondition = new UserTennisCourtCondition();
        courtCondition.setUid(100000);

        Page<TennisCourtMapEntity> hh = tennisCourtMapMapper.queryCollectPage(p, courtCondition);
        System.out.println(hh);

    }
}
