package com.chenfu.pojo;


public class ChessPiece implements Cloneable {
    public String key;
    public char color;
    public char character;
    public char index;
    public int[] position;

    public ChessPiece(String name, int[] position) {
        this.key = name;
        this.color = name.charAt(0);
        this.character = name.charAt(1);
        this.index = name.charAt(2);
        this.position = position;
    }
}
