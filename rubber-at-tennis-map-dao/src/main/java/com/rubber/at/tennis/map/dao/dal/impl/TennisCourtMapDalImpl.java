package com.rubber.at.tennis.map.dao.dal.impl;

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

}
