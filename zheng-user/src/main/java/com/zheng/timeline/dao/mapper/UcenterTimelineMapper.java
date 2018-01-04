package com.zheng.timeline.dao.mapper;

import com.zheng.timeline.dao.model.UcenterTimeline;
import com.zheng.timeline.model.UcenterTimelineVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcenterTimelineMapper {
    int deleteByPrimaryKey(Integer timelineId);

    int insert(UcenterTimeline record);

    int insertSelective(UcenterTimeline record);

    UcenterTimeline selectByPrimaryKey(Integer timelineId);

    int updateByPrimaryKeySelective(UcenterTimeline record);

    int updateByPrimaryKeyWithBLOBs(UcenterTimeline record);

    int updateByPrimaryKey(UcenterTimeline record);

    Map<String, Object> selectById(Integer timelineId);

    List<UcenterTimelineVO> selectFriendsTimelineList(@Param("uuid") String uuid,@Param("relationType") short relationType, @Param("date") long date);
}