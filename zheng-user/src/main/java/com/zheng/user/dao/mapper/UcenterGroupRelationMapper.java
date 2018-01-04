package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterGroupRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UcenterGroupRelationMapper {
    int deleteByPrimaryKey(Integer groupRelationId);

    int insert(UcenterGroupRelation record);

    int insertSelective(UcenterGroupRelation record);

    UcenterGroupRelation selectByPrimaryKey(Integer groupRelationId);

    int updateByPrimaryKeySelective(UcenterGroupRelation record);

    int updateByPrimaryKey(UcenterGroupRelation record);

    int insertBatch(List<UcenterGroupRelation> users);

    int deleteByTidAndUuid(@Param("tid") String tid,@Param("uuid") String uuid);

    int deleteByTid(String tid);

    int updateOwner(@Param("owner")String uuid,@Param("tid")String tid,@Param("new_owner")String member);

    int updateByTidAndUuid(UcenterGroupRelation groupRelation);

    int countGroupMember(String tid);

    UcenterGroupRelation selectByUuidAndTid(@Param("uuid")String uuid,@Param("tid")String tid);
}