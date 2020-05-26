package com.chenfu.adapter;

import com.chenfu.dialog.LoginDialog;
import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginAdapter extends MouseAdapter {

    private GameView gameView;
    private JLabel jLabel;
    private Color foreground;

    public LoginAdapter(GameView gameView, JLabel jLabel) {
        this.gameView = gameView;
        this.jLabel = jLabel;
        foreground = jLabel.getForeground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.showLoginDialog(gameView,jLabel);
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
