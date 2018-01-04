package com.zheng.user.dao.mapper;

import com.zheng.user.dao.model.UcenterUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UcenterUserRelationMapper {
    int deleteByPrimaryKey(Integer userRelationId);

    int insert(UcenterUserRelation record);

    int insertSelective(UcenterUserRelation record);

    UcenterUserRelation selectByPrimaryKey(Integer userRelationId);

    int updateByPrimaryKeySelective(UcenterUserRelation record);

    int updateByPrimaryKey(UcenterUserRelation record);

    UcenterUserRelation selectByUuid(@Param("uuid") String uuid, @Param("fuuid") String fuuid);

    int updateByUuid(UcenterUserRelation ucenterUserRelation);

    int deleteRelationByUuid(@Param("uuid")String uuid,@Param("fuuid")String fuuid);

    int updateBySelectUuid(UcenterUserRelation ucenterUserRelation);

    List<Map<String, Object>> selectUserFriends(@Param("uuid") String uuid, @Param("relationType") short[] relationType);

    List<UcenterUserRelation> selectByMultiId(@Param("uuid") String uuid, @Param("fuuid") String fuuid);

}