package com.zheng.user.dao.model;

import java.io.Serializable;

public class UcenterUserLog implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userLogId;

    /**
     * 用户编号
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 设备唯一ID
     *
     * @mbg.generated
     */
    private String deviceId;

    /**
     * 内容
     *
     * @mbg.generated
     */
    private String content;

    /**
     * 操作IP地址
     *
     * @mbg.generated
     */
    private String ip;

    /**
     * 操作环境
     *
     * @mbg.generated
     */
    private String agent;

    /**
     * 操作环境具体分类
     *
     * @mbg.generated
     */
    private Integer terminal;

    /**
     * 操作时间
     *
     * @mbg.generated
     */
    private Long createTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserLogId() {
        return userLogId;
    }

    public void setUserLogId(Integer userLogId) {
        this.userLogId = userLogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userLogId=").append(userLogId);
        sb.append(", userId=").append(userId);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", content=").append(content);
        sb.append(", ip=").append(ip);
        sb.append(", agent=").append(agent);
        sb.append(", terminal=").append(terminal);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UcenterUserLog other = (UcenterUserLog) that;
        return (this.getUserLogId() == null ? other.getUserLogId() == null : this.getUserLogId().equals(other.getUserLogId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getAgent() == null ? other.getAgent() == null : this.getAgent().equals(other.getAgent()))
            && (this.getTerminal() == null ? other.getTerminal() == null : this.getTerminal().equals(other.getTerminal()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserLogId() == null) ? 0 : getUserLogId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getAgent() == null) ? 0 : getAgent().hashCode());
        result = prime * result + ((getTerminal() == null) ? 0 : getTerminal().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}