package com.zheng.neo4j.rpc.service;

import com.zheng.neo4j.dao.domain.Neo4jFriend;
import com.zheng.neo4j.dao.domain.Neo4jUser;
import com.zheng.neo4j.rpc.api.Neo4jUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by linlinyeyu on 2017/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContext-dubbo-provider.xml"})
public class Neo4jRpcServiceTest {
    @Autowired
    private Neo4jUserService neo4JUserService;
    @Test
    public void neo4jUserTest(){
        Neo4jUser neo4jUser = new Neo4jUser();
        neo4jUser.setSex(1);
        neo4jUser.setMobile("13006198731");
        neo4jUser.setAvater("http://urls");
        neo4jUser.setName("linlinyeyu");
        neo4jUser.setBirthday(1512706577000L);
        neo4jUser.setActive((short)1);
        neo4jUser.setUuid("12980662");
        neo4jUser.setEmail("liuyubiaowu@hotmail.com");
        neo4jUser.setToken("08d5da4763598b6eb4e3a7749baddb4c");
        neo4jUser.setCreateTime(new Date().getTime());
        boolean flag = neo4JUserService.register(neo4jUser);
        System.out.println(flag);
    }

    @Test
    public void neo4jModifyRelationTest(){
        Neo4jFriend neo4jFriend = new Neo4jFriend();
        neo4jFriend.setAccid("12980662");
        neo4jFriend.setFaccid("13568070");
        neo4jFriend.setRelationType((short)2);
        boolean flag = neo4JUserService.modifyRelation(neo4jFriend);
        System.out.println(flag);
    }
}
