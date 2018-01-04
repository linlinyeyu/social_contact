package com.zheng.im.domain.message;

import java.io.Serializable;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class RecallMessage implements Serializable{
    private static final long serialVersionUID = 1L;

    private String deleteMsgid;
    private Long timetag;
    private Integer type;
    private String from;
    private String to;
    private String msg;
    private String ignoreTime;

    public String getDeleteMsgid() {
        return deleteMsgid;
    }

    public void setDeleteMsgid(String deleteMsgid) {
        this.deleteMsgid = deleteMsgid;
    }

    public Long getTimetag() {
        return timetag;
    }

    public void setTimetag(Long timetag) {
        this.timetag = timetag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(String ignoreTime) {
        this.ignoreTime = ignoreTime;
    }
}
