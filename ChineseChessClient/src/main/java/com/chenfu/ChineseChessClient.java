package com.chenfu;

import java.awt.*;

public class ChineseChessClient {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChessFrame chessFrame = new ChessFrame();
                chessFrame.setVisible(true);
            }
        });

    }
}
