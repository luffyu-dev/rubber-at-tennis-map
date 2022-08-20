package com.rubber.at.tennis.map.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest
@ComponentScan("com.rubber.at.tennis.*")
@MapperScan("com.rubber.at.tennis.**.dao.mapper")
class RubberServerArchetypeWebApplicationTests {


}
