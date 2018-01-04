package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserLog;

public interface UcenterUserLogMapper {
    int deleteByPrimaryKey(Integer userLogId);

    int insert(UcenterUserLog record);

    int insertSelective(UcenterUserLog record);

    UcenterUserLog selectByPrimaryKey(Integer userLogId);

    int updateByPrimaryKeySelective(UcenterUserLog record);

    int updateByPrimaryKey(UcenterUserLog record);
}