package com.zheng.neo4j.rpc.service;

import com.zheng.neo4j.dao.domain.Neo4jGroup;
import com.zheng.neo4j.dao.mapper.Neo4jGroupMapper;
import com.zheng.neo4j.rpc.api.Neo4jGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
@Transactional
public class Neo4jGroupServiceImpl implements Neo4jGroupService {
    @Autowired
    private Neo4jGroupMapper neo4jGroupMapper;

    @Override
    public boolean createGroup(Neo4jGroup neo4jGroup) {
        int count = neo4jGroupMapper.createGroup(neo4jGroup);
        if (count < 1){
            return false;
        }
        return addGroupRelation(neo4jGroup);
    }

    @Override
    public boolean addGroupRelation(Neo4jGroup neo4jGroup) {
        int count = neo4jGroupMapper.addGroupRelation(neo4jGroup);
        if (count < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteGroupRelation(String tid, String uuid, String members) {
        int count = neo4jGroupMapper.deleteGroupRelation(tid,uuid,members);
        if (count < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean removeGroup(String tid) {
        int count = neo4jGroupMapper.removeGroup(tid);
        if (count < 1){
            return false;
        }
        return true;
    }

    @Override
    public boolean isFamily(String tid) {
        int count = neo4jGroupMapper.FamilyCount(tid);
        if (count >= 10){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getGroupbyUser(String uuid, String fuuid) {
        return neo4jGroupMapper.getGroupbyUser(uuid,fuuid);
    }
}
