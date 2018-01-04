package com.zheng.timeline.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.timeline.dao.model.UcenterTimelineNotify;

import java.util.List;

/**
* UcenterTimelineNotifyService接口
* Created by pinet on 2017/12/29.
*/
public interface UcenterTimelineNotifyService extends BaseServicePinet<UcenterTimelineNotify> {

    int insertTimelineNotifyList(List<UcenterTimelineNotify> list);

}