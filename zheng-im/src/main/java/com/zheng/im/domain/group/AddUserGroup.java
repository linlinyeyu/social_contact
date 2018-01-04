package com.zheng.im.domain.group;

import java.io.Serializable;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class AddUserGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tid;
    private String owner;
    private String members;
    private Integer magree;
    private String msg;
    private String attach;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Integer getMagree() {
        return magree;
    }

    public void setMagree(Integer magree) {
        this.magree = magree;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
