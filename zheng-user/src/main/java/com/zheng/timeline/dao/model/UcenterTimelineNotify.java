package com.zheng.timeline.dao.model;

import java.io.Serializable;

public class UcenterTimelineNotify implements Serializable {
    private Integer id;

    private String userUuid;

    /**
     * 发布朋友圈的人的uuid
     *
     * @mbg.generated
     */
    private String friendUuid;

    private Integer timelineId;

    private Long createTime;

    /**
     * 发布时间，如果此朋友之前就是好友的话，那发布时间与创建时间一致，如果是之后加的好友的话，发布时间与此动态的时间一致
     *
     * @mbg.generated
     */
    private Long publishTime;

    /**
     * 是否已读，默认为0
     *
     * @mbg.generated
     */
    private Boolean isRead;

    private Long readTime;

    /**
     * 0为我，1为家族，2为朋友，3为同学，修改关系时需要修改对应的朋友圈关系
     *
     * @mbg.generated
     */
    private Short relationType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getFriendUuid() {
        return friendUuid;
    }

    public void setFriendUuid(String friendUuid) {
        this.friendUuid = friendUuid;
    }

    public Integer getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Integer timelineId) {
        this.timelineId = timelineId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }

    public Short getRelationType() {
        return relationType;
    }

    public void setRelationType(Short relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", friendUuid=").append(friendUuid);
        sb.append(", timelineId=").append(timelineId);
        sb.append(", createTime=").append(createTime);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", isRead=").append(isRead);
        sb.append(", readTime=").append(readTime);
        sb.append(", relationType=").append(relationType);
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
        UcenterTimelineNotify other = (UcenterTimelineNotify) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserUuid() == null ? other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid()))
            && (this.getFriendUuid() == null ? other.getFriendUuid() == null : this.getFriendUuid().equals(other.getFriendUuid()))
            && (this.getTimelineId() == null ? other.getTimelineId() == null : this.getTimelineId().equals(other.getTimelineId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getPublishTime() == null ? other.getPublishTime() == null : this.getPublishTime().equals(other.getPublishTime()))
            && (this.getIsRead() == null ? other.getIsRead() == null : this.getIsRead().equals(other.getIsRead()))
            && (this.getReadTime() == null ? other.getReadTime() == null : this.getReadTime().equals(other.getReadTime()))
            && (this.getRelationType() == null ? other.getRelationType() == null : this.getRelationType().equals(other.getRelationType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getFriendUuid() == null) ? 0 : getFriendUuid().hashCode());
        result = prime * result + ((getTimelineId() == null) ? 0 : getTimelineId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getPublishTime() == null) ? 0 : getPublishTime().hashCode());
        result = prime * result + ((getIsRead() == null) ? 0 : getIsRead().hashCode());
        result = prime * result + ((getReadTime() == null) ? 0 : getReadTime().hashCode());
        result = prime * result + ((getRelationType() == null) ? 0 : getRelationType().hashCode());
        return result;
    }
}