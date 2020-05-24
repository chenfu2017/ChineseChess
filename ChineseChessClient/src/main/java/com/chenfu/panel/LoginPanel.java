package com.chenfu.panel;

import com.chenfu.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private Image image;

    public LoginPanel() {
        image = ResourceUtils.getImage("login2.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,this.getWidth(), this.getHeight(), this);
    }
}
