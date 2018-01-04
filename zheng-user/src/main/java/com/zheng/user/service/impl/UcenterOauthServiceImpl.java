package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterOauthMapper;
import com.zheng.user.dao.model.UcenterOauth;
import com.zheng.user.service.UcenterOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterOauthService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterOauthServiceImpl extends BaseServiceImplPinet<UcenterOauthMapper, UcenterOauth> implements UcenterOauthService {

    private static Logger _log = LoggerFactory.getLogger(UcenterOauthServiceImpl.class);

    @Autowired
    UcenterOauthMapper ucenterOauthMapper;

}