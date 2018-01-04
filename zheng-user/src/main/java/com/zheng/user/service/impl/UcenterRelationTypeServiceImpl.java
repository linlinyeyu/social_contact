package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterRelationTypeMapper;
import com.zheng.user.dao.model.UcenterRelationType;
import com.zheng.user.service.UcenterRelationTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UcenterRelationTypeService实现
* Created by shuzheng on 2017/12/14.
*/
@Service
@Transactional
@BaseService
public class UcenterRelationTypeServiceImpl extends BaseServiceImplPinet<UcenterRelationTypeMapper, UcenterRelationType> implements UcenterRelationTypeService {

    private static Logger _log = LoggerFactory.getLogger(UcenterRelationTypeServiceImpl.class);

    @Autowired
    UcenterRelationTypeMapper ucenterRelationTypeMapper;

}