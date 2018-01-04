package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UcenterUserGroupMapper {
    int deleteByPrimaryKey(Integer userGroupId);

    int insert(UcenterUserGroup record);

    int insertSelective(UcenterUserGroup record);

    UcenterUserGroup selectByPrimaryKey(Integer userGroupId);

    int updateByPrimaryKeySelective(UcenterUserGroup record);

    int updateByPrimaryKey(UcenterUserGroup record);

    int updateByUuidAndYxid(UcenterUserGroup record);

    int deleteByYxTid(String yxId);

    int updateOwner(@Param("owner") String uuid,@Param("tid") String tid,@Param("new_owner")String member);

    Map<String,Object> getGroupQr(String tid);

    UcenterUserGroup selectByTid(String tid);

    UcenterUserGroup selectByPg(@Param("password") short password,@Param("geohash")String geohash);

    List<UcenterUserGroup> selectGroupsByTids(List<String> tids);
}