package com.zheng.im.domain.group;

import java.io.Serializable;

/**
 * Created by linlinyeyu on 2017/12/13.
 */
public class EditGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tid;
    private String tname;
    private String owner;
    private String announcement;
    private String intro;
    private Integer joinmode;
    private String custom;
    private String icon;
    private Integer beinvitemode;
    private Integer invitemode;
    private Integer uptinfomode;
    private Integer upcustommode;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getJoinmode() {
        return joinmode;
    }

    public void setJoinmode(Integer joinmode) {
        this.joinmode = joinmode;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getBeinvitemode() {
        return beinvitemode;
    }

    public void setBeinvitemode(Integer beinvitemode) {
        this.beinvitemode = beinvitemode;
    }

    public Integer getInvitemode() {
        return invitemode;
    }

    public void setInvitemode(Integer invitemode) {
        this.invitemode = invitemode;
    }

    public Integer getUptinfomode() {
        return uptinfomode;
    }

    public void setUptinfomode(Integer uptinfomode) {
        this.uptinfomode = uptinfomode;
    }

    public Integer getUpcustommode() {
        return upcustommode;
    }

    public void setUpcustommode(Integer upcustommode) {
        this.upcustommode = upcustommode;
    }
}
