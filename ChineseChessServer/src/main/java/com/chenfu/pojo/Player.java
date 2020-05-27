package com.chenfu.pojo;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 41L;

    @Id
    private String username;
    private String password;
    private Integer level;

    public Player() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equal(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
