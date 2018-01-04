package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserOauth;

public interface UcenterUserOauthMapper {
    int deleteByPrimaryKey(Integer userOauthId);

    int insert(UcenterUserOauth record);

    int insertSelective(UcenterUserOauth record);

    UcenterUserOauth selectByPrimaryKey(Integer userOauthId);

    int updateByPrimaryKeySelective(UcenterUserOauth record);

    int updateByPrimaryKeyWithBLOBs(UcenterUserOauth record);

    int updateByPrimaryKey(UcenterUserOauth record);
}