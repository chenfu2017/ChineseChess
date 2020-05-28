package com.chenfu.adapter;

import com.chenfu.dialog.LoginDialog;
import com.chenfu.netty.Client;
import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginAdapter extends MouseAdapter {

    private GameView gameView;
    private JLabel jLabel;
    private Color foreground;
    private Client client;

    public LoginAdapter(GameView gameView, JLabel jLabel) {
        this.gameView = gameView;
        this.jLabel = jLabel;
        foreground = jLabel.getForeground();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            client = Client.getInstance();
            client.setGameView(gameView);
            client.connect();
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.showLoginDialog(gameView,jLabel);
        }catch (Throwable t){
            gameView.getInformationBoard().AddLog("连接服务器失败！");
        }
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
