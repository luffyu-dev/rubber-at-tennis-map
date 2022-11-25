package com.rubber.at.tennis.map.service.apply;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.map.api.request.RegionCodeRequest;
import com.rubber.at.tennis.map.dao.dal.IUserCollectCourtDal;
import com.rubber.at.tennis.map.dao.entity.UserCollectCourtEntity;
import com.rubber.base.components.util.session.BaseUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2022/11/25
 */
@Service
public class UserCollectCourtService {


    @Autowired
    private IUserCollectCourtDal iUserCollectCourtDal;


    /**
     * 关注球场
     *
     * @param request 当前的请求
     * @return 返回是否成功
     */
    public void collectCourt(RegionCodeRequest request) {
        UserCollectCourtEntity collectInfo = getUserCollectInfo(request,request.getCourtCode());
        if (collectInfo == null){
            collectInfo = new UserCollectCourtEntity();
            collectInfo.setCourtCode(request.getCourtCode());
            collectInfo.setUid(request.getUid());
            collectInfo.setStatus(1);
            collectInfo.setCreateTime(new Date());
            collectInfo.setUpdateTime(new Date());
        }else if (collectInfo.getStatus() != 1){
            collectInfo.setStatus(1);
            collectInfo.setUpdateTime(new Date());
        }
        iUserCollectCourtDal.saveOrUpdate(collectInfo);
    }

    /**
     * 取消关注
     *
     * @param request 当前的请求
     * @return 返回是否成功
     */
    public void unCollectCourt(RegionCodeRequest request) {
        UserCollectCourtEntity collectInfo = getUserCollectInfo(request,request.getCourtCode());
        if (collectInfo != null && collectInfo.getStatus() != 0){
            collectInfo.setStatus(0);
            collectInfo.setUpdateTime(new Date());
            iUserCollectCourtDal.updateById(collectInfo);
        }
    }


    /**
     * 查询用户已经关注的球场
     * @param baseUserSession 当前的请求
     * @return 返回是否关注成功
     */
    public List<String> queryUserCollectedCourt(BaseUserSession baseUserSession){
        LambdaQueryWrapper<UserCollectCourtEntity> lqw = new LambdaQueryWrapper<>();
        lqw.select(UserCollectCourtEntity::getCourtCode)
                .eq(UserCollectCourtEntity::getUid,baseUserSession.getUid())
                .eq(UserCollectCourtEntity::getStatus,1)
                .orderByDesc(UserCollectCourtEntity::getUpdateTime);
        List<UserCollectCourtEntity> userCollectMap =  iUserCollectCourtDal.list(lqw);
        if (CollUtil.isEmpty(userCollectMap)){
            return new ArrayList<>();
        }
        return userCollectMap.stream().map(UserCollectCourtEntity::getCourtCode).collect(Collectors.toList());
    }



    /**
     * 通过关键信息查询是否关注球场
     */
    public UserCollectCourtEntity getUserCollectInfo(BaseUserSession baseUserSession,String courtKey){
        LambdaQueryWrapper<UserCollectCourtEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCollectCourtEntity::getCourtCode,courtKey)
                .eq(UserCollectCourtEntity::getUid,baseUserSession.getUid());
        return iUserCollectCourtDal.getOne(lqw);
    }
}
