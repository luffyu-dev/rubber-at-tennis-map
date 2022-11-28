package com.rubber.at.tennis.map.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.map.dao.condition.UserTennisCourtCondition;
import com.rubber.at.tennis.map.dao.entity.TennisCourtMapEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 网球场地地图 Mapper 接口
 * </p>
 *
 * @author rockyu
 * @since 2022-08-16
 */
public interface TennisCourtMapMapper extends BaseMapper<TennisCourtMapEntity> {


    /**
     * 只查询收藏的球场信息
     * @param page 当前的分页
     * @param entity 球场名称
     * @return 返回球场的信息
     */
    Page<TennisCourtMapEntity> queryCollectPage(Page<TennisCourtMapEntity> page
            ,@Param("entity") UserTennisCourtCondition entity);
}
