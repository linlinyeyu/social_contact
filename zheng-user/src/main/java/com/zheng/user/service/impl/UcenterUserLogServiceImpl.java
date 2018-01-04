package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterUserLogMapper;
import com.zheng.user.dao.model.UcenterUserLog;
import com.zheng.user.service.UcenterUserLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterUserLogService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterUserLogServiceImpl extends BaseServiceImplPinet<UcenterUserLogMapper, UcenterUserLog> implements UcenterUserLogService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserLogServiceImpl.class);

    @Autowired
    UcenterUserLogMapper ucenterUserLogMapper;

}