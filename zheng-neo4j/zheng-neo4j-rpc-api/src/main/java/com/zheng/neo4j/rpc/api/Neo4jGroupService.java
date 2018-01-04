package com.zheng.neo4j.rpc.api;

import com.zheng.neo4j.dao.domain.Neo4jGroup;

import java.util.List;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
public interface Neo4jGroupService {
    /**
     * 创建群节点
     * @param neo4jGroup
     * @return
     */
    boolean createGroup(Neo4jGroup neo4jGroup);

    /**
     * 添加群成员
     * @param neo4jGroup
     * @return
     */
    boolean addGroupRelation(Neo4jGroup neo4jGroup);

    /**
     * 踢除群成员
     * @param tid
     * @param uuid
     * @param members
     * @return
     */
    boolean deleteGroupRelation(String tid,String uuid,String members);

    /**
     * 解散群
     * @param tid
     * @return
     */
    boolean removeGroup(String tid);

    /**
     * 是否家族群
     * @param tid
     * @return
     */
    boolean isFamily(String tid);

    /**
     * 获取共同群
     * @param uuid
     * @param fuuid
     * @return
     */
    List<String> getGroupbyUser(String uuid,String fuuid);
}
