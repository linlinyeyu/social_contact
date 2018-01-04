package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterOauth;
import org.springframework.stereotype.Repository;

@Repository
public interface UcenterOauthMapper {
    int deleteByPrimaryKey(Integer oauthId);

    int insert(UcenterOauth record);

    int insertSelective(UcenterOauth record);

    UcenterOauth selectByPrimaryKey(Integer oauthId);

    int updateByPrimaryKeySelective(UcenterOauth record);

    int updateByPrimaryKey(UcenterOauth record);
}