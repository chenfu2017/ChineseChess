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

    @Override
    public String toString() {
        return "DataContent{" +
                "action=" + action +
                ", object=" + object +
                '}';
    }
}

