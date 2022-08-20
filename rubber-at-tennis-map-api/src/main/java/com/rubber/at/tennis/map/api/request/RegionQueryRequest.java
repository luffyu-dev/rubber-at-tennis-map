package com.rubber.at.tennis.map.api.request;

import com.rubber.base.components.util.result.page.BaseRequestPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/8/16
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RegionQueryRequest extends BaseRequestPage {


    /**
     * 地场key
     */
    private String courtCode;


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
     * 场地所在纬度
     */
    private String latitude;

    /**
     * 场地所在经度
     */
    private String longitude;
}
