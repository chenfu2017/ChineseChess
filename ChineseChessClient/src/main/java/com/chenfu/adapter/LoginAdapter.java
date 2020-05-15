package com.chenfu.adapter;

import com.chenfu.ChessFrame;
import com.chenfu.dialog.LoginDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginAdapter extends MouseAdapter {

    private ChessFrame chessFrame;
    private JLabel jLabel;
    private Color foreground;

    public LoginAdapter(ChessFrame chessFrame,JLabel jLabel) {
        this.chessFrame = chessFrame;
        this.jLabel = jLabel;
        foreground = jLabel.getForeground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.showLoginDialog(chessFrame,jLabel);
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
