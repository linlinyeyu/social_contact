package com.zheng.user.dao.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UcenterUserGroup implements Serializable {
    @JSONField(name = "user_group_id")
    private Integer userGroupId;

    /**
     * 群名称
     *
     * @mbg.generated
     */
    @JSONField(name = "group_name")
    private String groupName;

    /**
     * 群主
     *
     * @mbg.generated
     */
    private String owner;

    /**
     * 群公告
     *
     * @mbg.generated
     */
    private String announcement;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    @JSONField(name = "date_add")
    private Long dateAdd;

    /**
     * 是否可用
     *
     * @mbg.generated
     */
    private Byte status;

    @JSONField(name = "is_family")
    private Byte isFamily;
    @JSONField(name = "yx_tid")
    private String yxTid;
    private String icon;
    private Short password;
    private String geohash;
    private Byte isRemind;

    private static final long serialVersionUID = 1L;

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public Long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Long dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(Byte isFamily) {
        this.isFamily = isFamily;
    }

    public String getYxTid() {
        return yxTid;
    }

    public void setYxTid(String yxTid) {
        this.yxTid = yxTid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getPassword() {
        return password;
    }

    public void setPassword(Short password) {
        this.password = password;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public Byte getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Byte isRemind) {
        this.isRemind = isRemind;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userGroupId=").append(userGroupId);
        sb.append(", groupName=").append(groupName);
        sb.append(", owner=").append(owner);
        sb.append(", announcement=").append(announcement);
        sb.append(", dateAdd=").append(dateAdd);
        sb.append(", status=").append(status);
        sb.append(", isFamily=").append(isFamily);
        sb.append(", yxTid=").append(yxTid);
        sb.append(", icon=").append(icon);
        sb.append(", geohash=").append(geohash);
        sb.append(", password=").append(password);
        sb.append(", isRemind=").append(isRemind);
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
        UcenterUserGroup other = (UcenterUserGroup) that;
        return (this.getUserGroupId() == null ? other.getUserGroupId() == null : this.getUserGroupId().equals(other.getUserGroupId()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getOwner() == null ? other.getOwner() == null : this.getOwner().equals(other.getOwner()))
            && (this.getAnnouncement() == null ? other.getAnnouncement() == null : this.getAnnouncement().equals(other.getAnnouncement()))
            && (this.getDateAdd() == null ? other.getDateAdd() == null : this.getDateAdd().equals(other.getDateAdd()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsFamily() == null ? other.getIsFamily() == null : this.getIsFamily().equals(other.getIsFamily()))
            && (this.getYxTid() == null ? other.getYxTid() == null : this.getYxTid().equals(other.getYxTid()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getGeohash() == null ? other.getGeohash() == null : this.getGeohash().equals(other.getGeohash()))
            && (this.getIsRemind() == null ? other.getIsRemind() == null : this.getIsRemind().equals(other.getIsRemind()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserGroupId() == null) ? 0 : getUserGroupId().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getOwner() == null) ? 0 : getOwner().hashCode());
        result = prime * result + ((getAnnouncement() == null) ? 0 : getAnnouncement().hashCode());
        result = prime * result + ((getDateAdd() == null) ? 0 : getDateAdd().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsFamily() == null) ? 0 : getIsFamily().hashCode());
        result = prime * result + ((getYxTid() == null) ? 0 : getYxTid().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getGeohash() == null) ? 0 : getGeohash().hashCode());
        result = prime * result + ((getIsRemind() == null) ? 0 : getIsRemind().hashCode());
        return result;
    }
}