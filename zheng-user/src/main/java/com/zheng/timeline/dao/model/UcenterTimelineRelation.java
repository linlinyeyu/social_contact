package com.zheng.timeline.dao.model;

import java.io.Serializable;

public class UcenterTimelineRelation implements Serializable {
    private Integer id;

    private Integer timelineId;

    /**
     * 圈子类型，1为家族圈，2为朋友，3为同学
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

    public Integer getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Integer timelineId) {
        this.timelineId = timelineId;
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
        sb.append(", timelineId=").append(timelineId);
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
        UcenterTimelineRelation other = (UcenterTimelineRelation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTimelineId() == null ? other.getTimelineId() == null : this.getTimelineId().equals(other.getTimelineId()))
            && (this.getRelationType() == null ? other.getRelationType() == null : this.getRelationType().equals(other.getRelationType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTimelineId() == null) ? 0 : getTimelineId().hashCode());
        result = prime * result + ((getRelationType() == null) ? 0 : getRelationType().hashCode());
        return result;
    }
}