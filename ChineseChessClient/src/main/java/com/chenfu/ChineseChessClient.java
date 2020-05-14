package com.chenfu;

import java.awt.*;

public class ChineseChessClient {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChessFrame chessFrame = new ChessFrame();
                AudioPlayer audioPlayer = new AudioPlayer("bg.mp3", true);
                audioPlayer.start();
                chessFrame.setVisible(true);
            }
        });

    }
}
