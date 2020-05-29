package com.chenfu.components;

import com.chenfu.utils.ResourceUtils;
import javax.swing.*;
import java.awt.*;

public class DiyButton extends JButton {

    private Font buttonFont;
    private ImageIcon imageIcon;

    public DiyButton(String text,int x,int y,String filename) {
        imageIcon = ResourceUtils.getImageIcon(filename);
        buttonFont = new Font("宋体", Font.BOLD, 20);
        this.setBounds(x,y,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        this.setFont(buttonFont);
        this.setForeground(Color.BLUE);
        this.setText(text);
        this.setIcon(imageIcon);
        this.setOpaque(false);//设置控件是否透明，true为不透明，false为透明
        this.setContentAreaFilled(false);//设置图片填满按钮所在的区域
        this.setFocusPainted(false);//设置这个按钮是不是获得焦点
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setBorderPainted(false);//设置是否绘制边框
    }
}
