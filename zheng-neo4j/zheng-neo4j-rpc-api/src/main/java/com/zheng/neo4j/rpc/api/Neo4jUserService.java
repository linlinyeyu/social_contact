package com.zheng.neo4j.rpc.api;

import com.zheng.neo4j.dao.domain.Neo4jFriend;
import com.zheng.neo4j.dao.domain.Neo4jUser;

/**
 * Created by linlinyeyu on 2017/12/14.
 */
public interface Neo4jUserService {
    /**
     * 注册节点
     * @param neo4jUser
     * @return
     */
    boolean register(Neo4jUser neo4jUser);

    /**
     * 新增朋友关系
     * @param neo4jFriend
     * @return
     */
    boolean addFriend(Neo4jFriend neo4jFriend);

    /**
     * 删除朋友
     * @param neo4jFriend
     * @return
     */
    boolean delFriend(Neo4jFriend neo4jFriend);

    /**
     * 修改朋友关系
     * @param neo4jFriend
     * @return
     */
    boolean modifyRelation(Neo4jFriend neo4jFriend);
}
