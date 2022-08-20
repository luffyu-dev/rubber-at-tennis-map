package com.rubber.at.tennis.map.service.common.exception;

import com.rubber.base.components.util.result.code.ICodeHandle;

/**
 * @author luffyu
 * Created on 2022/8/14
 */
public enum  ErrorCodeEnums implements ICodeHandle {


    /**
     * 系统的code 从 1-**-**-** 开始
     *
     * 1-10-**-** 开始
     *
     * 2-10-**-** 系统类型错误
     *
     * 3-10-**-** 用户行为错误
     *
     */


    /**
     * 2-10-**-** 表示服务系统异常的提示
     */



    /**
     * 3-10-**-** 表示用户的行为错误
     */


    ;
    public final String code;

    public final String msg;


    ErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
