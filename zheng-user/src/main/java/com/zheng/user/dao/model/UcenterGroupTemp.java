package com.zheng.user.dao.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class UcenterGroupTemp implements Serializable {
    @JSONField(name = "group_temp_id")
    private Long groupTempId;

    private String uuid;

    private Short password;

    private String geohash;

    @JSONField(name = "date_add")
    private Long dateAdd;

    private static final long serialVersionUID = 1L;

    public Long getGroupTempId() {
        return groupTempId;
    }

    public void setGroupTempId(Long groupTempId) {
        this.groupTempId = groupTempId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Long getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Long dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupTempId=").append(groupTempId);
        sb.append(", uuid=").append(uuid);
        sb.append(", password=").append(password);
        sb.append(", geohash=").append(geohash);
        sb.append(", dateAdd=").append(dateAdd);
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
        UcenterGroupTemp other = (UcenterGroupTemp) that;
        return (this.getGroupTempId() == null ? other.getGroupTempId() == null : this.getGroupTempId().equals(other.getGroupTempId()))
              && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
              && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
              && (this.getGeohash() == null ? other.getGeohash() == null : this.getGeohash().equals(other.getGeohash()))
              && (this.getDateAdd() == null ? other.getDateAdd() == null : this.getDateAdd().equals(other.getDateAdd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupTempId() == null) ? 0 : getGroupTempId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getGeohash() == null) ? 0 : getGeohash().hashCode());
        result = prime * result + ((getDateAdd() == null) ? 0 : getDateAdd().hashCode());
        return result;
    }
}
