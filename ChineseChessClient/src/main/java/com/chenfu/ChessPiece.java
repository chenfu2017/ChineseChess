package com.chenfu;

import java.awt.Image;

public class ChessPiece {
    private int id;
    private Image image;
    private String fileName;

    /*    8~14依次表示红方的帅、仕、相、马、车、炮和兵；
          16~22依次表示黑方的将、士、象、马、车、炮和卒。*/
    public ChessPiece(int id) {
        this.id = id;
        switch (this.id) {
            case 16:
                fileName = "black-jiang";
                break;
            case 20:
                fileName = "black-ju";
                break;
            case 19:
                fileName = "black-ma";
                break;
            case 21:
                fileName = "black-pao";
                break;
            case 17:
                fileName = "black-shi";
                break;
            case 18:
                fileName = "black-xiang";
                break;
            case 22:
                fileName = "black-zu";
                break;
            case 14:
                fileName = "red-bing";
                break;
            case 12:
                fileName = "red-ju";
                break;
            case 11:
                fileName = "red-ma";
                break;
            case 13:
                fileName = "red-pao";
                break;
            case 9:
                fileName = "red-shi";
                break;
            case 8:
                fileName = "red-shuai";
                break;
            case 10:
                fileName = "red-xiang";
                break;
        }
        this.image = Utils.getImage(fileName + ".png");
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

