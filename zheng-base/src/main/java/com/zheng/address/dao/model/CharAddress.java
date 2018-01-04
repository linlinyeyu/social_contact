package com.zheng.address.dao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/6/6.
 */
public class CharAddress implements Serializable{

    private String firstChar;

    List<Address> addresses;

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
