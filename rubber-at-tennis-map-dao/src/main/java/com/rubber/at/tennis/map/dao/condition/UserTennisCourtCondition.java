package com.rubber.at.tennis.map.dao.condition;

import lombok.Data;

/**
 * @author luffyu
 * Created on 2022/11/28
 */
@Data
public class UserTennisCourtCondition {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 当前的大小
     */
    private Integer size;

    /**
     * uid
     */
    private Integer uid;

    /**
     * 球场名称
     */
    private String courtName;
}
