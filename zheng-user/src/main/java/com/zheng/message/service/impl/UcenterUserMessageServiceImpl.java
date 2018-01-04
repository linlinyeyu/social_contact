package com.zheng.message.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.message.dao.mapper.UcenterUserMessageMapper;
import com.zheng.message.dao.model.UcenterUserMessage;
import com.zheng.message.service.UcenterUserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterUserMessageService实现
* Created by shuzheng on 2017/12/25.
*/
@Service
@Transactional
@BaseService
public class UcenterUserMessageServiceImpl extends BaseServiceImplPinet<UcenterUserMessageMapper, UcenterUserMessage> implements UcenterUserMessageService {

    private static Logger _log = LoggerFactory.getLogger(UcenterUserMessageServiceImpl.class);

    @Autowired
    UcenterUserMessageMapper ucenterUserMessageMapper;


    @Override
    public boolean inserUserMessageList(List<UcenterUserMessage> messages) {
        int rows = ucenterUserMessageMapper.insertUserMessageList(messages);

        return rows > 0;
    }
}