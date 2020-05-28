package com.chenfu.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataContent implements Serializable {

    private static final long serialVersionUID = 42L;

    private int action;
    private Object object;

    public DataContent() {
    }

    public DataContent(Object object) {
        this.object = object;
    }

    public DataContent(int action, Object object) {
        this.action = action;
        this.object = object;
    }

    @Override
    public String toString() {
        return "DataContent{" +
                "action=" + action +
                ", object=" + object +
                '}';
    }
}

