package com.zheng.address.dao.model;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {
    private Integer id;

    private String name;

    private Integer parentId;

    private String shortName;

    private Short level;

    /**
     * 地址首字母
     *
     * @mbg.generated
     */
    private String pinyin;

    /**
     * 是否删除
     *
     * @mbg.generated
     */
    private Boolean isDeleted;

    /**
     * 下次版本发部的地址名称
     *
     * @mbg.generated
     */
    private String updateName;

    private Integer updateParentId;

    private String updateShortName;

    private Short updateLevel;

    private String updatePinyin;

    private Boolean updateIsDeleted;

    private List<Address> sonList;

    public List<Address> getSonList() {
        return sonList;
    }

    public void setSonList(List<Address> sonList) {
        this.sonList = sonList;
    }

    /**
     * 地址全称
     *
     * @mbg.generated
     */
    private String address;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Integer getUpdateParentId() {
        return updateParentId;
    }

    public void setUpdateParentId(Integer updateParentId) {
        this.updateParentId = updateParentId;
    }

    public String getUpdateShortName() {
        return updateShortName;
    }

    public void setUpdateShortName(String updateShortName) {
        this.updateShortName = updateShortName;
    }

    public Short getUpdateLevel() {
        return updateLevel;
    }

    public void setUpdateLevel(Short updateLevel) {
        this.updateLevel = updateLevel;
    }

    public String getUpdatePinyin() {
        return updatePinyin;
    }

    public void setUpdatePinyin(String updatePinyin) {
        this.updatePinyin = updatePinyin;
    }

    public Boolean getUpdateIsDeleted() {
        return updateIsDeleted;
    }

    public void setUpdateIsDeleted(Boolean updateIsDeleted) {
        this.updateIsDeleted = updateIsDeleted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", shortName=").append(shortName);
        sb.append(", level=").append(level);
        sb.append(", pinyin=").append(pinyin);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", updateName=").append(updateName);
        sb.append(", updateParentId=").append(updateParentId);
        sb.append(", updateShortName=").append(updateShortName);
        sb.append(", updateLevel=").append(updateLevel);
        sb.append(", updatePinyin=").append(updatePinyin);
        sb.append(", updateIsDeleted=").append(updateIsDeleted);
        sb.append(", address=").append(address);
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
        Address other = (Address) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getShortName() == null ? other.getShortName() == null : this.getShortName().equals(other.getShortName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getPinyin() == null ? other.getPinyin() == null : this.getPinyin().equals(other.getPinyin()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUpdateName() == null ? other.getUpdateName() == null : this.getUpdateName().equals(other.getUpdateName()))
            && (this.getUpdateParentId() == null ? other.getUpdateParentId() == null : this.getUpdateParentId().equals(other.getUpdateParentId()))
            && (this.getUpdateShortName() == null ? other.getUpdateShortName() == null : this.getUpdateShortName().equals(other.getUpdateShortName()))
            && (this.getUpdateLevel() == null ? other.getUpdateLevel() == null : this.getUpdateLevel().equals(other.getUpdateLevel()))
            && (this.getUpdatePinyin() == null ? other.getUpdatePinyin() == null : this.getUpdatePinyin().equals(other.getUpdatePinyin()))
            && (this.getUpdateIsDeleted() == null ? other.getUpdateIsDeleted() == null : this.getUpdateIsDeleted().equals(other.getUpdateIsDeleted()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getShortName() == null) ? 0 : getShortName().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getPinyin() == null) ? 0 : getPinyin().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUpdateName() == null) ? 0 : getUpdateName().hashCode());
        result = prime * result + ((getUpdateParentId() == null) ? 0 : getUpdateParentId().hashCode());
        result = prime * result + ((getUpdateShortName() == null) ? 0 : getUpdateShortName().hashCode());
        result = prime * result + ((getUpdateLevel() == null) ? 0 : getUpdateLevel().hashCode());
        result = prime * result + ((getUpdatePinyin() == null) ? 0 : getUpdatePinyin().hashCode());
        result = prime * result + ((getUpdateIsDeleted() == null) ? 0 : getUpdateIsDeleted().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        return result;
    }
}