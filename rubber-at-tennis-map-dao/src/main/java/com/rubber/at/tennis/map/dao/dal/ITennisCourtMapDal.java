package com.rubber.at.tennis.map.dao.dal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.map.dao.condition.UserTennisCourtCondition;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 网球场地地图 服务类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-16
 */
public interface ITennisCourtMapDal extends IBaseAdminService<TennisCourtMapEntity> {


    /**
     * 只查询收藏的球场信息
     * @param page 当前的分页
     * @param condition 球场名称
     * @return 返回球场的信息
     */
    Page<TennisCourtMapEntity> queryCollectPage(Page<TennisCourtMapEntity> page,UserTennisCourtCondition condition);


}
