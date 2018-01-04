package com.zheng.message.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.message.dao.mapper.MessageMapper;
import com.zheng.message.dao.model.Message;
import com.zheng.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* MessageService实现
* Created by shuzheng on 2017/12/25.
*/
@Service
@Transactional
@BaseService
public class MessageServiceImpl extends BaseServiceImplPinet<MessageMapper, Message> implements MessageService {

    private static Logger _log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

}