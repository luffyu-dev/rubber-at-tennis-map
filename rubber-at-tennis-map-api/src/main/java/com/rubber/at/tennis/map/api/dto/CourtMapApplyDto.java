package com.rubber.at.tennis.map.api.dto;

import com.alibaba.fastjson.JSONObject;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/8/16
 */
@Data
public class CourtMapApplyDto extends BaseUserSession {

    /**
     * 场地code
     */
    private String courtCode;

    /**
     * 场地名称
     */
    @NotNull(message = "场地名称不能为空")
    private String courtName;

    /**
     * 场地主图
     */
    @NotNull(message = "场地主图不能为空")
    private String homeImage;

    /**
     * 场地环境 最多支持4张图片
     */
    private String envImage;


    /**
     * 场地价目表
     */
    private String priceImage;


    /**
     * 场地数据
     */
    private Integer courtNum;

    /**
     * 10表示室外硬底 20表示风雨棚 30表示室内 40表示红土
     */
    @NotNull(message = "场地类型不能为空")
    private Integer courtType;

    /**
     * 场地所在省
     */
    @NotNull(message = "场地地址不能为空")
    private String province;

    /**
     * 场地所在市
     */
    @NotNull(message = "场地地址不能为空")
    private String city;

    /**
     * 场地所在区
     */
    @NotNull(message = "场地地址不能为空")
    private String district;

    /**
     * 场地详细地址
     */
    @NotNull(message = "场地地址不能为空")
    private String address;

    /**
     * 场地所在纬度
     */
    @NotNull(message = "场地地址不能为空")
    private String latitude;

    /**
     * 场地所在经度
     */
    @NotNull(message = "场地地址不能为空")
    private String longitude;

    /**
     * 场地简介
     */
    @NotNull(message = "场地地址不能为空")
    private String introduction;

    /**
     * 开放时间区间
     */
    @NotNull(message = "开放时间区间不能为空")
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
     * 标签集合
     */
    private List<String> courtTagList;

    /**
     * 价格详情
     */
    private String fee;

    /**
     * 价格详情
     */
    private JSONObject feeInfo;

}
