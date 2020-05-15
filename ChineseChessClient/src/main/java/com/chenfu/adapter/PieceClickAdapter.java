package com.chenfu.adapter;

import com.chenfu.AudioPlayer;
import com.chenfu.ChessBoard;
import com.chenfu.ChessPiece;
import com.chenfu.DefultSet;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class PieceClickAdapter extends MouseAdapter {

    private boolean selected = false;
    private ChessBoard chessBoard;

    public PieceClickAdapter(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AudioPlayer audioPlayer = new AudioPlayer("go.wav",false);
        int x, y;
        x = e.getX();
        y = e.getY();
        x = (x - DefultSet.ChessBoarderX) / DefultSet.chessBoarderP;
        y = (y - DefultSet.ChessBoarderY) / DefultSet.chessBoarderP;
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
                audioPlayer.play();
                selected = false;
                chessBoard.step++;
            }
        }
        chessBoard.repaint();
    }
}
