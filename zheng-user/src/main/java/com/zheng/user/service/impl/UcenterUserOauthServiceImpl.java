package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterUserOauthMapper;
import com.zheng.user.dao.model.UcenterUserOauth;
import com.zheng.user.service.UcenterUserOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterUserOauthService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterUserOauthServiceImpl extends BaseServiceImplPinet<UcenterUserOauthMapper, UcenterUserOauth> implements UcenterUserOauthService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserOauthServiceImpl.class);

    @Autowired
    UcenterUserOauthMapper ucenterUserOauthMapper;

}