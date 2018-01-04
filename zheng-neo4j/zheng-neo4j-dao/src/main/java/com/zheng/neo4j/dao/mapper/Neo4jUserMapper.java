package com.zheng.neo4j.dao.mapper;

import com.zheng.neo4j.dao.domain.Neo4jFriend;
import com.zheng.neo4j.dao.domain.Neo4jUser;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface Neo4jUserMapper {
    Map<String,Object> selectByName(String name);

    int insertSelect(Neo4jUser neo4jUser);

    int insertFriend(Neo4jFriend neo4jFriend);

    int deleteFriend(Neo4jFriend neo4jFriend);

    int updateRelation(Neo4jFriend neo4jFriend);
}
