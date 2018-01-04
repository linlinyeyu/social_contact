package com.zheng.timeline.dao.mapper;

import com.zheng.timeline.dao.model.UcenterTimelineEvent;
import com.zheng.timeline.model.UcenterTimelineEventVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UcenterTimelineEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterTimelineEvent record);

    int insertSelective(UcenterTimelineEvent record);

    UcenterTimelineEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterTimelineEvent record);

    int updateByPrimaryKey(UcenterTimelineEvent record);

    List<UcenterTimelineEventVO> selectEvents(List<Integer> timelineIds);

    List<UcenterTimelineEventVO> selectEventsByEventIds(List<Integer> eventIds);

    List<UcenterTimelineEventVO> selectEventsByUuid(@Param("uuid") String uuid, @Param("timelineId") int timelineId, @Param("eventType") short eventType);

    int deleteByUuidAndTimelineId(@Param("uuid") String uuid, @Param("timelineId") int timelineId, @Param("eventType") short eventType);
}