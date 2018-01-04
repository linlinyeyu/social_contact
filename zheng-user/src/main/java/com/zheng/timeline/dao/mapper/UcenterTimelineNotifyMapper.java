package com.zheng.timeline.dao.mapper;

import com.zheng.timeline.dao.model.UcenterTimelineNotify;

import java.util.List;

public interface UcenterTimelineNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterTimelineNotify record);

    int insertSelective(UcenterTimelineNotify record);

    UcenterTimelineNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterTimelineNotify record);

    int updateByPrimaryKey(UcenterTimelineNotify record);

    int insertTimelineNotifyList(List<UcenterTimelineNotify> list);
}