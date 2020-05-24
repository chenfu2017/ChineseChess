package com.chenfu.adapter;

import com.chenfu.ChessFrame;
import com.chenfu.InformationBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AIModeAdapter extends MouseAdapter {

    private ChessFrame chessFrame;
    private InformationBoard informationBoard;
    private JLabel jLabel;
    private Color foreground;

    public AIModeAdapter(ChessFrame chessFrame, InformationBoard informationBoard, JLabel jLabel) {
        this.chessFrame = chessFrame;
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
        chessFrame.setStatus(1);
        informationBoard.AddLog("AI combat mode selected!");
    }
}