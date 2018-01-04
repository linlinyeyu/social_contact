package com.zheng.user.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UcenterUserIdentity implements Serializable {
    private Integer id;

    /**
     * 用户id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 真实姓名
     *
     * @mbg.generated
     */
    private String realname;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private Short sex;

    /**
     * 身份证
     *
     * @mbg.generated
     */
        private String identityCard;

    /**
     * 身份证正面照
     *
     * @mbg.generated
     */
    private String frontImage;

    /**
     * 身份证背面照
     *
     * @mbg.generated
     */
    private String behindImage;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Long createTime;

    /**
     * 审核时间
     *
     * @mbg.generated
     */
    private Long auditTime;

    /**
     * 1为正在审核，2审核通过，3为审核失败
     *
     * @mbg.generated
     */
    private Short state;

    /**
     * 拒绝的回复理由
     *
     * @mbg.generated
     */
    private String reply;

    /**
     * 审核人id
     *
     * @mbg.generated
     */
    private Integer adminId;

    /**
     * 审核人名
     *
     * @mbg.generated
     */
    private String adminName;

    /**
     * 是否在通过后被系统驳回，0为否，1为是
     *
     * @mbg.generated
     */
    private Boolean isRejected;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBehindImage() {
        return behindImage;
    }

    public void setBehindImage(String behindImage) {
        this.behindImage = behindImage;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", realname=").append(realname);
        sb.append(", sex=").append(sex);
        sb.append(", identityCard=").append(identityCard);
        sb.append(", frontImage=").append(frontImage);
        sb.append(", behindImage=").append(behindImage);
        sb.append(", createTime=").append(createTime);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", state=").append(state);
        sb.append(", reply=").append(reply);
        sb.append(", adminId=").append(adminId);
        sb.append(", adminName=").append(adminName);
        sb.append(", isRejected=").append(isRejected);
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
        UcenterUserIdentity other = (UcenterUserIdentity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getIdentityCard() == null ? other.getIdentityCard() == null : this.getIdentityCard().equals(other.getIdentityCard()))
            && (this.getFrontImage() == null ? other.getFrontImage() == null : this.getFrontImage().equals(other.getFrontImage()))
            && (this.getBehindImage() == null ? other.getBehindImage() == null : this.getBehindImage().equals(other.getBehindImage()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAuditTime() == null ? other.getAuditTime() == null : this.getAuditTime().equals(other.getAuditTime()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getReply() == null ? other.getReply() == null : this.getReply().equals(other.getReply()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getAdminName() == null ? other.getAdminName() == null : this.getAdminName().equals(other.getAdminName()))
            && (this.getIsRejected() == null ? other.getIsRejected() == null : this.getIsRejected().equals(other.getIsRejected()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getIdentityCard() == null) ? 0 : getIdentityCard().hashCode());
        result = prime * result + ((getFrontImage() == null) ? 0 : getFrontImage().hashCode());
        result = prime * result + ((getBehindImage() == null) ? 0 : getBehindImage().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAuditTime() == null) ? 0 : getAuditTime().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getReply() == null) ? 0 : getReply().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getAdminName() == null) ? 0 : getAdminName().hashCode());
        result = prime * result + ((getIsRejected() == null) ? 0 : getIsRejected().hashCode());
        return result;
    }
}