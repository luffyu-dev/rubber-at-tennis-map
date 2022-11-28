package com.rubber.at.tennis.map.dao.dal.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.map.dao.condition.UserTennisCourtCondition;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.rubber.at.tennis.map.dao.mapper.TennisCourtMapMapper;
import com.rubber.at.tennis.map.dao.dal.ITennisCourtMapDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网球场地地图 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-16
 */
@Service
public class TennisCourtMapDalImpl extends BaseAdminService<TennisCourtMapMapper, TennisCourtMapEntity> implements ITennisCourtMapDal {


    /**
     * 只查询收藏的球场信息
     *
     * @param page   当前的分页
     * @param condition 球场名称
     * @return 返回球场的信息
     */
    @Override
    public Page<TennisCourtMapEntity> queryCollectPage(Page<TennisCourtMapEntity> page, UserTennisCourtCondition condition) {
        return getBaseMapper().queryCollectPage(page,condition);
    }
}
