package com.chenfu.dialog;

import com.chenfu.netty.Client;
import com.chenfu.panel.LoginPanel;
import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.MsgActionEnum;
import com.chenfu.pojo.Player;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog {

    private int width;
    private int height;
    private Client client;

    public LoginDialog() {
        this.width = 260;
        this.height = 200;
    }

    public LoginDialog(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void showLoginDialog(JFrame owner, JComponent parentComponent) {
        client = Client.getInstance();
        // 创建一个模态对话框
        JDialog dialog = new JDialog(owner, "用户登录", true);
        // 设置对话框的宽高
        dialog.setSize(width, height);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(null);

        JLabel jLabel1 = new JLabel("用户名 :");
        JTextField username = new JTextField(12);
        JLabel jLabel2 = new JLabel("密码 :");
        JPasswordField password = new JPasswordField(12);
        // 创建一个按钮用于关闭对话框
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = new Player(username.getText(),String.valueOf(password.getPassword()));
                DataContent dataContent = new DataContent();
                dataContent.setAction(MsgActionEnum.LOGIN.type);
                dataContent.setObject(player);
                client.getChannel().writeAndFlush(dataContent);
                dialog.dispose();
            }
        });
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });
        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        // 添加组件到面板
        loginPanel.add(jLabel1);
        loginPanel.add(username);
        loginPanel.add(jLabel2);
        loginPanel.add(password);
        loginPanel.add(loginButton);
        loginPanel.add(cancelButton);
        // 设置对话框的内容面板
        dialog.setContentPane(loginPanel);
        // 显示对话框
        dialog.setVisible(true);
    }

}
