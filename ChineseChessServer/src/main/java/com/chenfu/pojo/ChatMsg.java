package com.chenfu.pojo;

import java.io.Serializable;

public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 44L;

    private String username;
    private String msg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChatMsg(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
}
