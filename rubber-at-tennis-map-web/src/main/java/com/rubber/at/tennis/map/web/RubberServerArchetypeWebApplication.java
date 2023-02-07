package com.rubber.at.tennis.map.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.at.tennis.**.dao.mapper")
@SpringBootApplication
public class RubberServerArchetypeWebApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(RubberServerArchetypeWebApplication.class, args);
    }

}
