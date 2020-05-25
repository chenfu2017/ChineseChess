package com.chenfu.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private String username;
    private String password;
    private int level;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
