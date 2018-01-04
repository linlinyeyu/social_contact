package com.zheng.neo4j.dao.mapper;

import com.zheng.neo4j.dao.domain.Neo4jGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
@Repository
public interface Neo4jGroupMapper {
    int createGroup(Neo4jGroup neo4jGroup);

    int addGroupRelation(Neo4jGroup neo4jGroup);

    int deleteGroupRelation(@Param("tid") String tid,@Param("uuid")String uuid,@Param("members") String members);

    int removeGroup(String tid);

    int FamilyCount(String tid);

    List<String> getGroupbyUser(@Param("uuid")String uuid,@Param("fuuid")String fuuid);
}
