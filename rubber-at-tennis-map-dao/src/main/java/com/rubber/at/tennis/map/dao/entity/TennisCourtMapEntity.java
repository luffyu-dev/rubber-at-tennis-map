package com.rubber.at.tennis.map.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rubber.base.components.mysql.plugins.admin.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 网球场地地图
 * </p>
 *
 * @author rockyu
 * @since 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tennis_court_map")
public class TennisCourtMapEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "Fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 地场key
     */
    @TableField("Fcourt_code")
    private String courtCode;

    /**
     * 场地名称
     */
    @TableField("Fcourt_name")
    private String courtName;

    /**
     * 场地主图
     */
    @TableField("Fhome_image")
    private String homeImage;

    /**
     * 场地环境 最多支持4张图片
     */
    @TableField("Fenv_image")
    private String envImage;

    /**
     * 价格图片
     */
    @TableField("Fprice_image")
    private String priceImage;

    /**
     * 10表示室外硬底 20表示风雨棚 30表示室内 40表示红土
     */
    @TableField("Fcourt_type")
    private Integer courtType;

    /**
     * 场地数据
     */
    @TableField("Fcourt_num")
    private Integer courtNum;

    /**
     * 场地所在省
     */
    @TableField("Fprovince")
    private String province;

    /**
     * 场地所在市
     */
    @TableField("Fcity")
    private String city;

    /**
     * 场地所在区
     */
    @TableField("Fdistrict")
    private String district;

    /**
     * 场地详细地址
     */
    @TableField("Faddress")
    private String address;

    /**
     * 场地所在纬度
     */
    @TableField("Flatitude")
    private String latitude;

    /**
     * 场地所在经度
     */
    @TableField("Flongitude")
    private String longitude;

    /**
     * 场地简介
     */
    @TableField("Fintroduction")
    private String introduction;

    /**
     * 开放时间区间
     */
    @TableField("Fopen_hours")
    private String openHours;

    /**
     * 预定方式 1表示微信 2表示电话 3表示线上
     */
    @TableField("Freserve_type")
    private Integer reserveType;

    /**
     * 预定详情
     */
    @TableField("Freserve_info")
    private String reserveInfo;

    /**
     * 场地管理员
     */
    @TableField("Fmanager")
    private String manager;

    /**
     * 场地报告者
     */
    @TableField("Freporter")
    private String reporter;

    /**
     * 环境评分
     */
    @TableField("Fenv_score")
    private Integer envScore;

    /**
     * 预定难度
     */
    @TableField("Freserve_diff")
    private Integer reserveDiff;

    /**
     * 综合评分
     */
    @TableField("Fscore")
    private Integer score;

    /**
     * 备注
     */
    @TableField("Fremark")
    private String remark;

    /**
     * 版本号
     */
    @TableField("Fversion")
    private Integer version;

    /**
     * 状态 10表示初始化 20表示待审核 30表示审核不通过 50表示审核通过
     */
    @TableField("Fstatus")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("Fcreate_time")
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @TableField("Fupdate_time")
    private Date updateTime;


}
