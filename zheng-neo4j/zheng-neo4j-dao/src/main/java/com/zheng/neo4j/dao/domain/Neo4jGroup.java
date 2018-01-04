package com.zheng.neo4j.dao.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by linlinyeyu on 2017/12/19.
 */
public class Neo4jGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String tid;
    private List<String> members;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
