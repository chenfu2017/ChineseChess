package com.chenfu.adapter;

import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExitAdapter extends MouseAdapter{

    private GameView gameView;
    private JLabel jLabel;
    private Color foreground;

    public ExitAdapter(GameView gameView, JLabel jLabel) {
        this.gameView = gameView;
        this.jLabel = jLabel;
        foreground = jLabel.getForeground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameView.dispose();
        System.exit(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        jLabel.setForeground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        jLabel.setForeground(foreground);
    }
}
