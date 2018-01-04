package com.zheng.timeline.dao.mapper;

import com.zheng.timeline.dao.model.UcenterTimelineRelation;

import java.util.List;

public interface UcenterTimelineRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterTimelineRelation record);

    int insertSelective(UcenterTimelineRelation record);

    UcenterTimelineRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterTimelineRelation record);

    int updateByPrimaryKey(UcenterTimelineRelation record);

    int insertTimelineRelationList(List<UcenterTimelineRelation> list);
}