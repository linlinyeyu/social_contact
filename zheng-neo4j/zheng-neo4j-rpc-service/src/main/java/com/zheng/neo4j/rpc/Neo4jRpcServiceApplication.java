package com.zheng.neo4j.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by linlinyeyu on 2017/12/15.
 */
public class Neo4jRpcServiceApplication {
    private static Logger logger = LoggerFactory.getLogger(Neo4jRpcServiceApplication.class);
    public static void main(String []args) throws IOException {
        logger.info("neo4j初始化");
        new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");
        logger.info("neo4j初始化完成");

    }
}
