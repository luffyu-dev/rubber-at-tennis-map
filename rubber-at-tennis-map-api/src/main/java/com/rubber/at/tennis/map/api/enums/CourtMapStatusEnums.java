package com.rubber.at.tennis.map.api.enums;

import lombok.Getter;

/**
 * @author luffyu
 * Created on 2022/6/11
 */
@Getter
public enum CourtMapStatusEnums {

    /**
     * 停用
     */
    CLOSE(10),

    /**
     * 审批中
     */
    APPROVEING(20),

    /**
     * 审批不通过
     */
    UN_APPROVE(30),

    /**
     * 上线中
     */
    ON(50);

    ;

    CourtMapStatusEnums(Integer status) {
        this.status = status;
    }

    private final Integer status;

}
