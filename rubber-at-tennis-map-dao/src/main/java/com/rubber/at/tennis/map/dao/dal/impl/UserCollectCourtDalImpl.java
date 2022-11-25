package com.rubber.at.tennis.map.dao.dal.impl;

import com.rubber.at.tennis.map.dao.entity.UserCollectCourtEntity;
import com.rubber.at.tennis.map.dao.mapper.UserCollectMapMapper;
import com.rubber.at.tennis.map.dao.dal.IUserCollectCourtDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户球场收藏表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-11-24
 */
@Service
public class UserCollectCourtDalImpl extends BaseAdminService<UserCollectMapMapper, UserCollectCourtEntity> implements IUserCollectCourtDal {

}
