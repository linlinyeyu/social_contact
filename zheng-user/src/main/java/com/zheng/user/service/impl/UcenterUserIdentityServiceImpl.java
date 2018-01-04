package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterUserIdentityMapper;
import com.zheng.user.dao.model.UcenterUserIdentity;
import com.zheng.user.service.UcenterUserIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterUserIdentityService实现
* Created by shuzheng on 2017/12/20.
*/
@Service
@Transactional
@BaseService
public class UcenterUserIdentityServiceImpl extends BaseServiceImplPinet<UcenterUserIdentityMapper, UcenterUserIdentity> implements UcenterUserIdentityService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserIdentityServiceImpl.class);

    @Autowired
    UcenterUserIdentityMapper ucenterUserIdentityMapper;

    @Override
    public UcenterUserIdentity selectLastIdentity(int userId){
        return ucenterUserIdentityMapper.selectLastIdentity(userId);
    }

    @Override
    public UcenterUserIdentity selectPassedIdentity(int userId){
        return ucenterUserIdentityMapper.selectPassedIdentity(userId);
    }

    @Override
    public UcenterUserIdentity selectPassedOrAuditIdentity(int userId){
        return ucenterUserIdentityMapper.selectPassedOrAuditIdentity(userId);
    }

}