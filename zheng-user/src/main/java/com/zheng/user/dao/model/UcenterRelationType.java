package com.zheng.user.dao.model;

import java.io.Serializable;

public class UcenterRelationType implements Serializable {
    private Byte relationType;

    /**
     * 关系名称
     *
     * @mbg.generated
     */
    private String name;
    private Short isFamily;
    private Short strength;

    private static final long serialVersionUID = 1L;

    public Byte getRelationType() {
        return relationType;
    }

    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(Short isFamily) {
        this.isFamily = isFamily;
    }

    public Short getStrength() {
        return strength;
    }

    public void setStrength(Short strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relationType=").append(relationType);
        sb.append(", name=").append(name);
        sb.append(", isFamily=").append(isFamily);
        sb.append(", strength=").append(strength);
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
        UcenterRelationType other = (UcenterRelationType) that;
        return (this.getRelationType() == null ? other.getRelationType() == null : this.getRelationType().equals(other.getRelationType()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getIsFamily() == null ? other.getIsFamily() == null : this.getIsFamily().equals(other.getIsFamily()))
            && (this.getStrength() == null ? other.getStrength() == null : this.getStrength().equals(other.getStrength()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRelationType() == null) ? 0 : getRelationType().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getIsFamily() == null) ? 0:getIsFamily().hashCode());
        result = prime * result + ((getStrength() == null) ? 0:getStrength().hashCode());
        return result;
    }
}