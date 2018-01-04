package com.zheng.timeline.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.timeline.dao.model.UcenterTimelineRelation;

import java.util.List;

/**
* UcenterTimelineRelationService接口
* Created by pinet on 2017/12/28.
*/
public interface UcenterTimelineRelationService extends BaseServicePinet<UcenterTimelineRelation> {

    int insertTimelineRelationList(List<UcenterTimelineRelation> list);
}