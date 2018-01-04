package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserDetails;

public interface UcenterUserDetailsMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UcenterUserDetails record);

    int insertSelective(UcenterUserDetails record);

    UcenterUserDetails selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UcenterUserDetails record);

    int updateByPrimaryKey(UcenterUserDetails record);
}