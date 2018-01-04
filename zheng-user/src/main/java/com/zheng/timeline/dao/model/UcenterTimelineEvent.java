package com.zheng.timeline.dao.model;

import java.io.Serializable;

public class UcenterTimelineEvent implements Serializable {
    private Integer id;

    private Integer timelineId;

    /**
     * 点赞的人
     *
     * @mbg.generated
     */
    private String userUuid;

    /**
     * 被点赞的朋友圈的发布人
     *
     * @mbg.generated
     */
    private String targetUuid;

    /**
     * 被回复的event的id
     *
     * @mbg.generated
     */
    private Integer targetId;

    /**
     * 评论内容
     *
     * @mbg.generated
     */
    private String text;

    /**
     * 事件类型，共2个，1、点赞laud，2、评论comment
     *
     * @mbg.generated
     */
    private Short eventType;

    private Long createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Integer timelineId) {
        this.timelineId = timelineId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getTargetUuid() {
        return targetUuid;
    }

    public void setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Short getEventType() {
        return eventType;
    }

    public void setEventType(Short eventType) {
        this.eventType = eventType;
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
        sb.append(", id=").append(id);
        sb.append(", timelineId=").append(timelineId);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", targetUuid=").append(targetUuid);
        sb.append(", targetId=").append(targetId);
        sb.append(", text=").append(text);
        sb.append(", eventType=").append(eventType);
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
        UcenterTimelineEvent other = (UcenterTimelineEvent) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTimelineId() == null ? other.getTimelineId() == null : this.getTimelineId().equals(other.getTimelineId()))
            && (this.getUserUuid() == null ? other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid()))
            && (this.getTargetUuid() == null ? other.getTargetUuid() == null : this.getTargetUuid().equals(other.getTargetUuid()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getEventType() == null ? other.getEventType() == null : this.getEventType().equals(other.getEventType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTimelineId() == null) ? 0 : getTimelineId().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getTargetUuid() == null) ? 0 : getTargetUuid().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getEventType() == null) ? 0 : getEventType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}