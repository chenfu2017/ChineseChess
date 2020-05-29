package com.chenfu.pojo;

import java.io.Serializable;
import java.util.Arrays;

public class PieceMsg implements Serializable {

    private static final long serialVersionUID = 43L;
    private String username;
    private String key;
    private int[] srcPos;
    private int[] desPos;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int[] getSrcPos() {
        return srcPos;
    }

    public void setSrcPos(int[] srcPos) {
        this.srcPos = srcPos;
    }

    public int[] getDesPos() {
        return desPos;
    }

    public void setDesPos(int[] desPos) {
        this.desPos = desPos;
    }


    public PieceMsg(String username, String key, int[] srcPos, int[] desPos) {
        this.username = username;
        this.key = key;
        this.srcPos = srcPos;
        this.desPos = desPos;
    }

    @Override
    public String toString() {
        return "PieceMsg{" +
                "username='" + username + '\'' +
                ", key='" + key + '\'' +
                ", srcPos=" + Arrays.toString(srcPos) +
                ", desPos=" + Arrays.toString(desPos) +
                '}';
    }
}
