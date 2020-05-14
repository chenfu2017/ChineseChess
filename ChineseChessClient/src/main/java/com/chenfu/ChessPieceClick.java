package com.chenfu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ChessPieceClick extends MouseAdapter {

    private boolean selected = false;
    private ChessBoard chessBoard;


    public ChessPieceClick(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y;
        x = e.getX();
        y = e.getY();
        x = (x - DefultSet.ChessBoarderXX) / DefultSet.ChessBoarderPP;
        y = (y - DefultSet.ChessBoarderYY) / DefultSet.ChessBoarderPP;
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return;
        }
        if (!selected) {
            ChessPiece chessPiece = chessBoard.chessPieces[y][x];
            if (chessPiece != null) {
                int id = chessPiece.getId();
                if ((id & 8) != 0 && chessBoard.step % 2 == 0 || (id & 16) != 0 && chessBoard.step % 2 == 1) {
                    chessBoard.setPoint(new Point(x, y));
                    selected = true;
                }
            }
        } else {
            Point src = chessBoard.getPoint();
            Point des = new Point(x, y);
            if (chessBoard.eatPiece(src, des)) {
                chessBoard.chessPieces[des.y][des.x] = chessBoard.chessPieces[src.y][src.x];
                chessBoard.chessPieces[src.y][src.x] = null;
                chessBoard.setPoint(des);
                AudioPlayer audioPlayer = new AudioPlayer("go.mp3");
                audioPlayer.start();
                selected = false;
                chessBoard.step++;
            }
        }
        chessBoard.repaint();
    }
}
