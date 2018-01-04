package com.zheng.user.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.user.dao.mapper.UcenterGroupTempMapper;
import com.zheng.user.dao.model.UcenterGroupTemp;
import com.zheng.user.service.UcenterGroupTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@BaseService
public class UcenterGroupTempServiceImpl extends BaseServiceImplPinet<UcenterGroupTempMapper,UcenterGroupTemp> implements UcenterGroupTempService {
    private static Logger _log = LoggerFactory.getLogger(UcenterGroupTempServiceImpl.class);
    @Autowired
    private UcenterGroupTempMapper ucenterGroupTempMapper;
}
