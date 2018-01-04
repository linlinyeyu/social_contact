package com.zheng.im.domain.message;

import java.io.Serializable;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class ImMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private String from;
    private Integer ope;
    private String to;
    private Integer type;
    private String body;
    private String antispam;
    private String antispamCustom;
    private String option;
    private String pushcontent;
    private String payload;
    private String ext;
    private String forcepushlist;
    private String forcepushcontent;
    private String forcepushall;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getOpe() {
        return ope;
    }

    public void setOpe(Integer ope) {
        this.ope = ope;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAntispam() {
        return antispam;
    }

    public void setAntispam(String antispam) {
        this.antispam = antispam;
    }

    public String getAntispamCustom() {
        return antispamCustom;
    }

    public void setAntispamCustom(String antispamCustom) {
        this.antispamCustom = antispamCustom;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPushcontent() {
        return pushcontent;
    }

    public void setPushcontent(String pushcontent) {
        this.pushcontent = pushcontent;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getForcepushlist() {
        return forcepushlist;
    }

    public void setForcepushlist(String forcepushlist) {
        this.forcepushlist = forcepushlist;
    }

    public String getForcepushcontent() {
        return forcepushcontent;
    }

    public void setForcepushcontent(String forcepushcontent) {
        this.forcepushcontent = forcepushcontent;
    }

    public String getForcepushall() {
        return forcepushall;
    }

    public void setForcepushall(String forcepushall) {
        this.forcepushall = forcepushall;
    }
}
