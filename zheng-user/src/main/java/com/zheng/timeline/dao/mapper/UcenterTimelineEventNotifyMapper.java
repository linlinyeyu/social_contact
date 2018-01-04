package com.zheng.timeline.dao.mapper;

import com.zheng.timeline.dao.model.UcenterTimelineEventNotify;

import java.util.List;
import java.util.Map;

public interface UcenterTimelineEventNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterTimelineEventNotify record);

    int insertSelective(UcenterTimelineEventNotify record);

    UcenterTimelineEventNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterTimelineEventNotify record);

    int updateByPrimaryKey(UcenterTimelineEventNotify record);

    int insertTimelineEventNotifys(List<UcenterTimelineEventNotify> list);

    int deleteByEventId(int eventId);

    int selectCountByUuid(String uuid);

    List<Map<String, Object>> selectUsersNotifies(String uuid);

    int removeAllUnreadNotify(String uuid);
}