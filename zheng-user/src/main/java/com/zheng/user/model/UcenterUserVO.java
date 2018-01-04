package com.zheng.user.model;

import com.zheng.user.dao.model.UcenterUser;

/**
 * Created by acer on 2017/12/29.
 */
public class UcenterUserVO extends UcenterUser {

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
