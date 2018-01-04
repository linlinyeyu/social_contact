package com.zheng.timeline.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UcenterTimeline implements Serializable {
    private Integer timelineId;

    /**
     * 发布人uuid
     *
     * @mbg.generated
     */
    private String userUuid;

    private String username;

    private String avatar;

    /**
     * 类型，1为纯文本类型，2为有图片，3为有视频，4为有关联链接
     *
     * @mbg.generated
     */
    private Short mediaType;

    private Long createTime;

    /**
     * 是否已删除
     *
     * @mbg.generated
     */
    private Boolean isDeleted;

    /**
     * 是否被举报，1为被举报，后台可设置为未被举报状态，被举报时，只有自己可见
     *
     * @mbg.generated
     */
    private Boolean isReported;

    private Long updateTime;

    /**
     * 关系列表，以逗号隔开
     *
     * @mbg.generated
     */
    private String relationTypes;

    private String text;

    /**
     * 内容
     *
     * @mbg.generated
     */
    private String body;

    private static final long serialVersionUID = 1L;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Short getMediaType() {
        return mediaType;
    }

    public void setMediaType(Short mediaType) {
        this.mediaType = mediaType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsReported() {
        return isReported;
    }

    public void setIsReported(Boolean isReported) {
        this.isReported = isReported;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRelationTypes() {
        return relationTypes;
    }

    public void setRelationTypes(String relationTypes) {
        this.relationTypes = relationTypes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", timelineId=").append(timelineId);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", username=").append(username);
        sb.append(", avatar=").append(avatar);
        sb.append(", mediaType=").append(mediaType);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", isReported=").append(isReported);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", relationTypes=").append(relationTypes);
        sb.append(", text=").append(text);
        sb.append(", body=").append(body);
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
        UcenterTimeline other = (UcenterTimeline) that;
        return (this.getTimelineId() == null ? other.getTimelineId() == null : this.getTimelineId().equals(other.getTimelineId()))
            && (this.getUserUuid() == null ? other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getMediaType() == null ? other.getMediaType() == null : this.getMediaType().equals(other.getMediaType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getIsReported() == null ? other.getIsReported() == null : this.getIsReported().equals(other.getIsReported()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRelationTypes() == null ? other.getRelationTypes() == null : this.getRelationTypes().equals(other.getRelationTypes()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getBody() == null ? other.getBody() == null : this.getBody().equals(other.getBody()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTimelineId() == null) ? 0 : getTimelineId().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getMediaType() == null) ? 0 : getMediaType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getIsReported() == null) ? 0 : getIsReported().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRelationTypes() == null) ? 0 : getRelationTypes().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getBody() == null) ? 0 : getBody().hashCode());
        return result;
    }
}