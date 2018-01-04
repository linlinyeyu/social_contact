package com.zheng.action.dao.model;

import java.io.Serializable;

public class Action implements Serializable {
    private Integer actionId;

    /**
     * 动作名字
     *
     * @mbg.generated
     */
    private String name;

    /**
     * IOS跳转路径
     *
     * @mbg.generated
     */
    private String iosParam;

    /**
     * 安卓跳转路径
     *
     * @mbg.generated
     */
    private String androidParam;

    /**
     * 移动端网页跳转路径
     *
     * @mbg.generated
     */
    private String wapParam;

    /**
     * PC网页跳转路径
     *
     * @mbg.generated
     */
    private String webParam;

    /**
     * 跳转的页面是否需要登录
     *
     * @mbg.generated
     */
    private Boolean needLogin;

    private static final long serialVersionUID = 1L;

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIosParam() {
        return iosParam;
    }

    public void setIosParam(String iosParam) {
        this.iosParam = iosParam;
    }

    public String getAndroidParam() {
        return androidParam;
    }

    public void setAndroidParam(String androidParam) {
        this.androidParam = androidParam;
    }

    public String getWapParam() {
        return wapParam;
    }

    public void setWapParam(String wapParam) {
        this.wapParam = wapParam;
    }

    public String getWebParam() {
        return webParam;
    }

    public void setWebParam(String webParam) {
        this.webParam = webParam;
    }

    public Boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", actionId=").append(actionId);
        sb.append(", name=").append(name);
        sb.append(", iosParam=").append(iosParam);
        sb.append(", androidParam=").append(androidParam);
        sb.append(", wapParam=").append(wapParam);
        sb.append(", webParam=").append(webParam);
        sb.append(", needLogin=").append(needLogin);
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
        Action other = (Action) that;
        return (this.getActionId() == null ? other.getActionId() == null : this.getActionId().equals(other.getActionId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIosParam() == null ? other.getIosParam() == null : this.getIosParam().equals(other.getIosParam()))
            && (this.getAndroidParam() == null ? other.getAndroidParam() == null : this.getAndroidParam().equals(other.getAndroidParam()))
            && (this.getWapParam() == null ? other.getWapParam() == null : this.getWapParam().equals(other.getWapParam()))
            && (this.getWebParam() == null ? other.getWebParam() == null : this.getWebParam().equals(other.getWebParam()))
            && (this.getNeedLogin() == null ? other.getNeedLogin() == null : this.getNeedLogin().equals(other.getNeedLogin()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getActionId() == null) ? 0 : getActionId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIosParam() == null) ? 0 : getIosParam().hashCode());
        result = prime * result + ((getAndroidParam() == null) ? 0 : getAndroidParam().hashCode());
        result = prime * result + ((getWapParam() == null) ? 0 : getWapParam().hashCode());
        result = prime * result + ((getWebParam() == null) ? 0 : getWebParam().hashCode());
        result = prime * result + ((getNeedLogin() == null) ? 0 : getNeedLogin().hashCode());
        return result;
    }
}