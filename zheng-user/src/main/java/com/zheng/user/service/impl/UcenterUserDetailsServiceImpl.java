package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterUserDetailsMapper;
import com.zheng.user.dao.model.UcenterUserDetails;
import com.zheng.user.service.UcenterUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterUserDetailsService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterUserDetailsServiceImpl extends BaseServiceImplPinet<UcenterUserDetailsMapper, UcenterUserDetails> implements UcenterUserDetailsService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserDetailsServiceImpl.class);

    @Autowired
    UcenterUserDetailsMapper ucenterUserDetailsMapper;

}