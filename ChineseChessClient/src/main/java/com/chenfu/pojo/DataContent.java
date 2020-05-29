package com.chenfu.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataContent implements Serializable {

    private static final long serialVersionUID = 42L;
    private int action;		// 动作类型
    private Object object;

    public DataContent() {
    }

    public DataContent(Object object) {
        this.object = object;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "DataContent{" +
                "action=" + action +
                ", object=" + object +
                '}';
    }
}

