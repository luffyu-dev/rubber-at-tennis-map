package com.rubber.at.tennis.map.api.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;


/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Data
public class TennisCourtMapDto {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 地场key
     */
    private String courtCode;

    /**
     * 场地名称
     */
    private String courtName;

    /**
     * 场地主图
     */
    private String homeImage;

    /**
     * 场地环境 最多支持4张图片
     */
    private String envImage;


    /**
     * 价格照片
     */
    private String priceImage;

    /**
     * 10表示室外硬底 20表示风雨棚 30表示室内 40表示红土
     */
    private Integer courtType;


    /**
     * 场地数量
     */
    private Integer courtNum;

    /**
     * 场地所在省
     */
    private String province;

    /**
     * 场地所在市
     */
    private String city;

    /**
     * 场地所在区
     */
    private String district;

    /**
     * 场地详细地址
     */
    private String address;

    /**
     * 场地所在纬度
     */
    private String latitude;

    /**
     * 场地所在经度
     */
    private String longitude;

    /**
     * 场地简介
     */
    private String introduction;

    /**
     * 开放时间区间
     */
    private String openHours;

    /**
     * 预定方式 1表示微信 2表示电话 3表示线上
     */
    private Integer reserveType;

    /**
     * 预定详情
     */
    private JSONObject reserveInfo;

    /**
     * 场地管理员
     */
    private String manager;

    /**
     * 场地报告者
     */
    private String reporter;

    /**
     * 环境评分
     */
    private Integer envScore;

    /**
     * 预定难度
     */
    private Integer reserveDiff;

    /**
     * 综合评分
     */
    private Integer score;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 10表示初始化 20表示待审核 30表示审核不通过 50表示审核通过
     */
    private Integer status;


    /**
     * lbs的定位距离
     * 单位是米
     */
    private Integer lbsDistance = 0;


    /**
     * 是否收藏了球场
     */
    private boolean collected;
}
