package com.rubber.at.tennis.map.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luffyu
 * Created on 2024/3/17
 *
 * {
 *         "courtType": "室外硬地",
 *         "time1": "8:00",
 *         "time2": "17:00",
 *         "fee": 80,
 *         "day2": "周五",
 *         "day1": "周一"
 *     }
 */
@Data
public class CourtFeeGroupDto {

    /**
     * 周一到周五
     */
    private String timeKey;


    private List<CourtFeeDto> feeDtoList = new ArrayList<>();


    public CourtFeeGroupDto(String timeKey) {
        this.timeKey = timeKey;
    }

    @Data
    public static class CourtFeeDto{

        private String timeKey;

        private String courtType;

        private String time1;

        private String time2;

        private Integer fee;

        private String day1;

        private String day2;
    }

}



