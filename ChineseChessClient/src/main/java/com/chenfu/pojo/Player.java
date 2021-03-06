package com.chenfu.pojo;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter

public class Player implements Serializable {

    private static final long serialVersionUID = 41L;

    private String username;
    private String password;
    private Integer level;

    public Player() {
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Player(String username, String password, int level) {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
