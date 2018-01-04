package com.zheng.user.dao.model;

import java.io.Serializable;

public class UcenterUser implements Serializable {
    /**
     * 编号
     *
     * @mbg.generated
     */
    private Integer userId;

    private String uuid;

    /**
     * 密码(MD5(密码))
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 昵称
     *
     * @mbg.generated
     */
    private String nickname;

    private String realname;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 性别(0:未知,1:男,2:女)
     *
     * @mbg.generated
     */
    private Byte sex;

    /**
     * 头像
     *
     * @mbg.generated
     */
    private String avatar;

    private String email;

    private Long birthday;

    /**
     * 0为未认证，1为审核中，2为已认证
     *
     * @mbg.generated
     */
    private Short isIdentity;

    /**
     * 1-可用，0-禁用
     *
     * @mbg.generated
     */
    private Byte active;

    /**
     * 省id
     *
     * @mbg.generated
     */
    private Integer provinceId;

    /**
     * 市id
     *
     * @mbg.generated
     */
    private Integer cityId;

    /**
     * 区域
     *
     * @mbg.generated
     */
    private String area;

    /**
     * 注册时间
     *
     * @mbg.generated
     */
    private Long createTime;

    /**
     * 注册IP地址
     *
     * @mbg.generated
     */
    private String createIp;

    /**
     * 最后登录时间
     *
     * @mbg.generated
     */
    private Long lastLoginTime;

    /**
     * 最后登录IP地址
     *
     * @mbg.generated
     */
    private String lastLoginIp;

    /**
     * 登录token
     *
     * @mbg.generated
     */
    private String token;

    /**
     * token 过期时间,为创建token、使用token 的15天后
     *
     * @mbg.generated
     */
    private Long tokenExpireTime;

    /**
     * 极光推送token
     *
     * @mbg.generated
     */
    private String jpushToken;

    /**
     * 云信的token
     *
     * @mbg.generated
     */
    private String yxToken;

    /**
     * 终端
     *
     * @mbg.generated
     */
    private Short terminal;

    /**
     * 是否被删除，1为删除，0为未被删除
     *
     * @mbg.generated
     */
    private Boolean isDeleted;

    /**
     * 是否需要推送，默认为1
     *
     * @mbg.generated
     */
    private Boolean needPush;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Short getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(Short isIdentity) {
        this.isIdentity = isIdentity;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getJpushToken() {
        return jpushToken;
    }

    public void setJpushToken(String jpushToken) {
        this.jpushToken = jpushToken;
    }

    public String getYxToken() {
        return yxToken;
    }

    public void setYxToken(String yxToken) {
        this.yxToken = yxToken;
    }

    public Short getTerminal() {
        return terminal;
    }

    public void setTerminal(Short terminal) {
        this.terminal = terminal;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getNeedPush() {
        return needPush;
    }

    public void setNeedPush(Boolean needPush) {
        this.needPush = needPush;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", uuid=").append(uuid);
        sb.append(", password=").append(password);
        sb.append(", nickname=").append(nickname);
        sb.append(", realname=").append(realname);
        sb.append(", phone=").append(phone);
        sb.append(", sex=").append(sex);
        sb.append(", avatar=").append(avatar);
        sb.append(", email=").append(email);
        sb.append(", birthday=").append(birthday);
        sb.append(", isIdentity=").append(isIdentity);
        sb.append(", active=").append(active);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", cityId=").append(cityId);
        sb.append(", area=").append(area);
        sb.append(", createTime=").append(createTime);
        sb.append(", createIp=").append(createIp);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", token=").append(token);
        sb.append(", tokenExpireTime=").append(tokenExpireTime);
        sb.append(", jpushToken=").append(jpushToken);
        sb.append(", yxToken=").append(yxToken);
        sb.append(", terminal=").append(terminal);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", needPush=").append(needPush);
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
        UcenterUser other = (UcenterUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getRealname() == null ? other.getRealname() == null : this.getRealname().equals(other.getRealname()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getIsIdentity() == null ? other.getIsIdentity() == null : this.getIsIdentity().equals(other.getIsIdentity()))
            && (this.getActive() == null ? other.getActive() == null : this.getActive().equals(other.getActive()))
            && (this.getProvinceId() == null ? other.getProvinceId() == null : this.getProvinceId().equals(other.getProvinceId()))
            && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
            && (this.getArea() == null ? other.getArea() == null : this.getArea().equals(other.getArea()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateIp() == null ? other.getCreateIp() == null : this.getCreateIp().equals(other.getCreateIp()))
            && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
            && (this.getLastLoginIp() == null ? other.getLastLoginIp() == null : this.getLastLoginIp().equals(other.getLastLoginIp()))
            && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getTokenExpireTime() == null ? other.getTokenExpireTime() == null : this.getTokenExpireTime().equals(other.getTokenExpireTime()))
            && (this.getJpushToken() == null ? other.getJpushToken() == null : this.getJpushToken().equals(other.getJpushToken()))
            && (this.getYxToken() == null ? other.getYxToken() == null : this.getYxToken().equals(other.getYxToken()))
            && (this.getTerminal() == null ? other.getTerminal() == null : this.getTerminal().equals(other.getTerminal()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getNeedPush() == null ? other.getNeedPush() == null : this.getNeedPush().equals(other.getNeedPush()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getRealname() == null) ? 0 : getRealname().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getIsIdentity() == null) ? 0 : getIsIdentity().hashCode());
        result = prime * result + ((getActive() == null) ? 0 : getActive().hashCode());
        result = prime * result + ((getProvinceId() == null) ? 0 : getProvinceId().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getArea() == null) ? 0 : getArea().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateIp() == null) ? 0 : getCreateIp().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + ((getLastLoginIp() == null) ? 0 : getLastLoginIp().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getTokenExpireTime() == null) ? 0 : getTokenExpireTime().hashCode());
        result = prime * result + ((getJpushToken() == null) ? 0 : getJpushToken().hashCode());
        result = prime * result + ((getYxToken() == null) ? 0 : getYxToken().hashCode());
        result = prime * result + ((getTerminal() == null) ? 0 : getTerminal().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getNeedPush() == null) ? 0 : getNeedPush().hashCode());
        return result;
    }
}