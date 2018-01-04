package com.zheng.timeline.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.common.util.WowCollections;
import com.zheng.timeline.dao.mapper.UcenterTimelineNotifyMapper;
import com.zheng.timeline.dao.model.UcenterTimelineNotify;
import com.zheng.timeline.service.UcenterTimelineNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterTimelineNotifyService实现
* Created by shuzheng on 2017/12/29.
*/
@Service
@Transactional
@BaseService
public class UcenterTimelineNotifyServiceImpl extends BaseServiceImplPinet<UcenterTimelineNotifyMapper, UcenterTimelineNotify> implements UcenterTimelineNotifyService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTimelineNotifyServiceImpl.class);

    @Autowired
    UcenterTimelineNotifyMapper ucenterTimelineNotifyMapper;

    @Override
    public int insertTimelineNotifyList(List<UcenterTimelineNotify> list){
        WowCollections.uniqList(list);
        return ucenterTimelineNotifyMapper.insertTimelineNotifyList(list);
    }

}