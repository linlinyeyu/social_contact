package com.zheng.message.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.message.dao.model.UcenterUserMessage;

import java.util.List;

/**
* UcenterUserMessageService接口
* Created by pinet on 2017/12/25.
*/
public interface UcenterUserMessageService extends BaseServicePinet<UcenterUserMessage> {

    boolean inserUserMessageList(List<UcenterUserMessage> messages);
}