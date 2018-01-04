package com.zheng.neo4j.rpc.service;

import com.zheng.neo4j.dao.domain.Neo4jFriend;
import com.zheng.neo4j.dao.domain.Neo4jUser;
import com.zheng.neo4j.dao.mapper.Neo4jUserMapper;
import com.zheng.neo4j.rpc.api.Neo4jUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by linlinyeyu on 2017/12/14.
 */
@Transactional
public class Neo4jUserServiceImpl implements Neo4jUserService {
    @Autowired
    private Neo4jUserMapper neo4jUserMapper;

    @Override
    public boolean register(Neo4jUser neo4jUser) {
        int count = neo4jUserMapper.insertSelect(neo4jUser);
        if (count > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addFriend(Neo4jFriend neo4jFriend) {
        int count = neo4jUserMapper.insertFriend(neo4jFriend);
        if (count > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean delFriend(Neo4jFriend neo4jFriend) {
        int count = neo4jUserMapper.deleteFriend(neo4jFriend);
        if (count > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyRelation(Neo4jFriend neo4jFriend) {
        try {
            neo4jUserMapper.updateRelation(neo4jFriend);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
