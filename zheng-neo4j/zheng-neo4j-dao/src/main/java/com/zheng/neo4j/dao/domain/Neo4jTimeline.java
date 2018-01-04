package com.zheng.neo4j.dao.domain;

/**
 * Created by acer on 2017/12/23.
 */
public class Neo4jTimeline {

    private String uuid;

    private Integer type;

    private Integer id;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
