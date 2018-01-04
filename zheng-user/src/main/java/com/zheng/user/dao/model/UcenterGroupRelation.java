package com.zheng.user.dao.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UcenterGroupRelation implements Serializable {
    private Integer groupRelationId;

    /**
     * 云信群id
     *
     * @mbg.generated
     */
    @JSONField(name = "group_yx_id")
    private String groupYxId;

    /**
     * 群成员id
     *
     * @mbg.generated
     */
    private String uuid;

    /**
     * 是否管理员
     *
     * @mbg.generated
     */
    @JSONField(name = "is_member")
    private Byte isMember;

    /**
     * 是否群主
     *
     * @mbg.generated
     */
    @JSONField(name = "is_owner")
    private Byte isOwner;

    private String alias;
    @JSONField(name = "is_top")
    private Byte isTop;

    @JSONField(name = "is_add_mail")
    private Byte isAddMail;

    @JSONField(name = "is_mute_team")
    private Byte isMuteTeam;

    private static final long serialVersionUID = 1L;

    public Integer getGroupRelationId() {
        return groupRelationId;
    }

    public void setGroupRelationId(Integer groupRelationId) {
        this.groupRelationId = groupRelationId;
    }

    public String getGroupYxId() {
        return groupYxId;
    }

    public void setGroupYxId(String groupYxId) {
        this.groupYxId = groupYxId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Byte getIsMember() {
        return isMember;
    }

    public void setIsMember(Byte isMember) {
        this.isMember = isMember;
    }

    public Byte getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Byte isOwner) {
        this.isOwner = isOwner;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Byte getIsTop() {
        return isTop;
    }

    public void setIsTop(Byte isTop) {
        this.isTop = isTop;
    }

    public Byte getIsAddMail() {
        return isAddMail;
    }

    public void setIsAddMail(Byte isAddMail) {
        this.isAddMail = isAddMail;
    }

    public Byte getIsMuteTeam() {
        return isMuteTeam;
    }

    public void setIsMuteTeam(Byte isMuteTeam) {
        this.isMuteTeam = isMuteTeam;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupRelationId=").append(groupRelationId);
        sb.append(", groupYxId=").append(groupYxId);
        sb.append(", uuid=").append(uuid);
        sb.append(", isMember=").append(isMember);
        sb.append(", isOwner=").append(isOwner);
        sb.append(", alias=").append(alias);
        sb.append(", isTop=").append(isTop);
        sb.append(", isAddMail=").append(isAddMail);
        sb.append(", isMuteTeam=").append(isMuteTeam);
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
        UcenterGroupRelation other = (UcenterGroupRelation) that;
        return (this.getGroupRelationId() == null ? other.getGroupRelationId() == null : this.getGroupRelationId().equals(other.getGroupRelationId()))
            && (this.getGroupYxId() == null ? other.getGroupYxId() == null : this.getGroupYxId().equals(other.getGroupYxId()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getIsMember() == null ? other.getIsMember() == null : this.getIsMember().equals(other.getIsMember()))
            && (this.getIsOwner() == null ? other.getIsOwner() == null : this.getIsOwner().equals(other.getIsOwner()))
            && (this.getAlias() == null ? other.getAlias() == null : this.getAlias().equals(other.getAlias()))
            && (this.getIsTop() == null ? other.getIsTop() == null : this.getIsTop().equals(other.getIsTop()))
            && (this.getIsAddMail() == null ? other.getIsAddMail() == null : this.getIsAddMail().equals(other.getIsAddMail()))
            && (this.getIsMuteTeam() == null ? other.getIsMuteTeam() == null : this.getIsMuteTeam().equals(other.getIsMuteTeam()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupRelationId() == null) ? 0 : getGroupRelationId().hashCode());
        result = prime * result + ((getGroupYxId() == null) ? 0 : getGroupYxId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getIsMember() == null) ? 0 : getIsMember().hashCode());
        result = prime * result + ((getIsOwner() == null) ? 0 : getIsOwner().hashCode());
        result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
        result = prime * result + ((getIsTop() == null) ? 0 : getIsTop().hashCode());
        result = prime * result + ((getIsAddMail() == null) ? 0 : getIsAddMail().hashCode());
        result = prime * result + ((getIsMuteTeam() == null) ? 0 : getIsMuteTeam().hashCode());
        return result;
    }
}