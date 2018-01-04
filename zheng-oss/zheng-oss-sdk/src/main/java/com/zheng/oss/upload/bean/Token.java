package com.zheng.oss.upload.bean;

/**
 * Created by acer on 2017/12/13.
 */
public class Token {
    private String token;

    private long deadline;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public Token(String token, long deadline) {
        this.token = token;
        this.deadline = deadline;
    }
}
