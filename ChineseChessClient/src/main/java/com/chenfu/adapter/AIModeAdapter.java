package com.chenfu.adapter;

import com.chenfu.view.GameView;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.inform.InformationBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AIModeAdapter extends MouseAdapter {

    private GameView gameView;
    private InformationBoard informationBoard;
    private JLabel jLabel;
    private Color foreground;

    public AIModeAdapter(GameView gameView, InformationBoard informationBoard, JLabel jLabel) {
        this.gameView = gameView;
        this.informationBoard = informationBoard;
        this.jLabel = jLabel;
        this.foreground = jLabel.getForeground();
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
        informationBoard.AddLog("AI combat mode selected!");
        gameView.getStepTimer().start();
        gameView.getTotalTimer().start();
        gameView.repaint();
    }
}