package com.chenfu.adapter;

import com.chenfu.*;
import com.chenfu.chessboard.ChessBoard;
import com.chenfu.chessboard.ChessPiece;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.utils.AudioPlayer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PieceClickAdapter extends MouseAdapter {

    private boolean selected;
    private ChessBoard chessBoard;
    private ChessFrame chessFrame;

    public PieceClickAdapter(ChessBoard chessBoard, ChessFrame chessFrame) {
        this.chessBoard = chessBoard;
        this.chessFrame = chessFrame;
    }

    private boolean chooseOwnPiece(int id){
        return (id & 8) != 0 && chessBoard.step % 2 == 0 || (id & 16) != 0 && chessBoard.step % 2 == 1;
    }

    private void goChess(Point srcpoint, Point despoint) {
        chessBoard.chessPieces[despoint.y][despoint.x] = chessBoard.chessPieces[srcpoint.y][srcpoint.x];
        chessBoard.chessPieces[srcpoint.y][srcpoint.x] = null;
        chessBoard.setPoint(despoint);
        selected = false;
        chessBoard.step++;
        chessFrame.getStepTimer().reStart();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int status = chessFrame.status;
        if(status == GameStatusEnum.AI_START.status || status ==GameStatusEnum.NETWORK_START.status){
            Point srcpoint = chessBoard.getPoint();
            AudioPlayer audioPlayer = new AudioPlayer("go.wav", false);
            int x, y;
            x = e.getX();
            y = e.getY();
            x = (x - DefultSet.chessBoarderX) / DefultSet.chessBoarderP;
            y = (y - DefultSet.chessBoarderY) / DefultSet.chessBoarderP;
            if (x < 0 || x > 8 || y < 0 || y > 9) {
                return;
            }
//        System.out.println(x+","+y);
            ChessPiece chessPiece = chessBoard.chessPieces[y][x];
            Point despoint = new Point(x, y);
            if (chessPiece != null) {
                int id = chessPiece.getId();
                if (chooseOwnPiece(id)) {
                    if (srcpoint == null) {
                        chessBoard.setPoint(new Point(x, y));
                    } else {
                        chessBoard.getPoint().x = x;
                        chessBoard.getPoint().y = y;
                    }
                    selected = true;
                } else {
                    if (selected && !chooseOwnPiece(id) && chessBoard.eatPiece(srcpoint, despoint)) {
                        goChess(srcpoint, despoint);
                        audioPlayer.play();
                    }
                }
            } else {
                if (selected && chessBoard.move(srcpoint, despoint)) {
                    goChess(srcpoint, despoint);
                    audioPlayer.play();
                }
            }
            chessBoard.repaint();
        } else {
            chessFrame.getInformationBoard().AddLog("please select game mode!");
        }
    }
}
