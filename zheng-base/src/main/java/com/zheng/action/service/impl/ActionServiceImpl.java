package com.zheng.action.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.action.dao.mapper.ActionMapper;
import com.zheng.action.dao.model.Action;
import com.zheng.action.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ActionService实现
* Created by shuzheng on 2017/12/25.
*/
@Service
@Transactional
@BaseService
public class ActionServiceImpl extends BaseServiceImplPinet<ActionMapper, Action> implements ActionService {

    private static Logger _log = LoggerFactory.getLogger(ActionServiceImpl.class);

    @Autowired
    ActionMapper actionMapper;

}