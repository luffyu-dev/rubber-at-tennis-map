package com.rubber.at.tennis.map.api.request;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luffyu
 * Created on 2022/11/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegionCodeRequest extends BaseUserSession {

    /**
     * 地场key
     */
    private String courtCode;

}
