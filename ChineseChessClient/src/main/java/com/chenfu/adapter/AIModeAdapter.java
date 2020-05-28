package com.chenfu.adapter;

import com.chenfu.chess.AImodeThread;
import com.chenfu.chess.ChessBoard;
import com.chenfu.control.GameController;
import com.chenfu.view.GameView;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.inform.InformationBoard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AIModeAdapter extends MouseAdapter {

    private GameView gameView;
    private ChessBoard chessBoard;
    private GameController gameController;
    private JLabel jLabel;
    private Color foreground;

    public AIModeAdapter(GameView gameView, ChessBoard chessBoard, GameController gameController, JLabel jLabel) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.gameController = gameController;
        this.jLabel = jLabel;
        foreground = jLabel.getForeground();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        jLabel.setForeground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        jLabel.setForeground(foreground);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameView.status = GameStatusEnum.AI_START.status;
        gameView.getInformationBoard().AddLog("AI combat mode selected!");
        new Thread(new AImodeThread(gameView,chessBoard,gameController)).start();
    }
}