package com.zheng.timeline.service;

import com.zheng.common.base.BaseServicePinet;
import com.zheng.timeline.dao.model.UcenterTimelineEventNotify;

import java.util.List;
import java.util.Map;

/**
* UcenterTimelineEventNotifyService接口
* Created by pinet on 2017/12/29.
*/
public interface UcenterTimelineEventNotifyService extends BaseServicePinet<UcenterTimelineEventNotify> {

    /**
     * 插入信息，在列表里会去重
     * */
    int insertTimelineEventNotifys(List<UcenterTimelineEventNotify> list);

    int selectNotifyCount(String uuid);

    int deleteByEventId(int eventId);

    List<Map<String, Object>> selectUsersNotifies(String uuid);

    int removeAllUnreadNotify(String uuid);
}