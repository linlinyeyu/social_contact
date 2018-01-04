package com.zheng.timeline.service.impl;

import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.timeline.dao.mapper.UcenterTimelineRelationMapper;
import com.zheng.timeline.dao.model.UcenterTimelineRelation;
import com.zheng.timeline.service.UcenterTimelineRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* UcenterTimelineRelationService实现
* Created by shuzheng on 2017/12/28.
*/
@Service
@Transactional
@BaseService
public class UcenterTimelineRelationServiceImpl extends BaseServiceImplPinet<UcenterTimelineRelationMapper, UcenterTimelineRelation> implements UcenterTimelineRelationService {

    private static Logger _log = LoggerFactory.getLogger(UcenterTimelineRelationServiceImpl.class);

    @Autowired
    UcenterTimelineRelationMapper ucenterTimelineRelationMapper;


    @Override
    public int insertTimelineRelationList(List<UcenterTimelineRelation> list) {
        return ucenterTimelineRelationMapper.insertTimelineRelationList(list);
    }
}