package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserIdentity;

public interface UcenterUserIdentityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UcenterUserIdentity record);

    int insertSelective(UcenterUserIdentity record);

    UcenterUserIdentity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UcenterUserIdentity record);

    int updateByPrimaryKey(UcenterUserIdentity record);

    UcenterUserIdentity selectPassedOrAuditIdentity(int userId);

    UcenterUserIdentity selectPassedIdentity(int userId);

    UcenterUserIdentity selectLastIdentity(int userId);
}