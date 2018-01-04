package com.zheng.user.dao.model;

import java.io.Serializable;

public class UcenterUserRelation implements Serializable {
    private Integer userRelationId;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private String userUuid;

    /**
     * 朋友id
     *
     * @mbg.generated
     */
    private String friendUuid;

    /**
     * 关系
     *
     * @mbg.generated
     */
    private Byte relationType;

    /**
     * 添加时间
     *
     * @mbg.generated
     */
    private Long dateAdd;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    private Long dateUpd;

    private String alias;

    private Short status;

    private static final long serialVersionUID = 1L;

    public Integer getUserRelationId() {
        return userRelationId;
    }

    public void setUserRelationId(Integer userRelationId) {
        this.userRelationId = userRelationId;
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

    public Byte getRelationType() {
        return relationType;
    }

    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    public Long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Long dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Long getDateUpd() {
        return dateUpd;
    }

    public void setDateUpd(Long dateUpd) {
        this.dateUpd = dateUpd;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userRelationId=").append(userRelationId);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", friendUuid=").append(friendUuid);
        sb.append(", relationType=").append(relationType);
        sb.append(", dateAdd=").append(dateAdd);
        sb.append(", dateUpd=").append(dateUpd);
        sb.append(", alias=").append(alias);
        sb.append(", status=").append(status);
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
        UcenterUserRelation other = (UcenterUserRelation) that;
        return (this.getUserRelationId() == null ? other.getUserRelationId() == null : this.getUserRelationId().equals(other.getUserRelationId()))
            && (this.getUserUuid() == null ? other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid()))
            && (this.getFriendUuid() == null ? other.getFriendUuid() == null : this.getFriendUuid().equals(other.getFriendUuid()))
            && (this.getRelationType() == null ? other.getRelationType() == null : this.getRelationType().equals(other.getRelationType()))
            && (this.getDateAdd() == null ? other.getDateAdd() == null : this.getDateAdd().equals(other.getDateAdd()))
            && (this.getDateUpd() == null ? other.getDateUpd() == null : this.getDateUpd().equals(other.getDateUpd()))
            && (this.getAlias() == null ? other.getAlias() == null : this.getAlias().equals(other.getAlias()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserRelationId() == null) ? 0 : getUserRelationId().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getFriendUuid() == null) ? 0 : getFriendUuid().hashCode());
        result = prime * result + ((getRelationType() == null) ? 0 : getRelationType().hashCode());
        result = prime * result + ((getDateAdd() == null) ? 0 : getDateAdd().hashCode());
        result = prime * result + ((getDateUpd() == null) ? 0 : getDateUpd().hashCode());
        result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
        result = prime * result + ((getStatus() == null ? 0 : getStatus().hashCode()));
        return result;
    }
}