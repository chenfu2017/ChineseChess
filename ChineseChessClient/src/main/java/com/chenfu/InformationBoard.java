package com.chenfu;

import com.chenfu.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InformationBoard extends JPanel {

    private Image image;
    //记录数据
    StringBuffer log = new StringBuffer();
    //用于储存最后10行
    String[] logS = new String[10];
    //Add方法的插入位置
    int Address = 0;

    public InformationBoard() {
        super();
        image = ResourceUtils.getImage("information.png");
        //设置界面透明
        this.setOpaque(false);
        //初始化显示的10行
        for (int i = 0; i < 10; i++) {
            logS[i] = new String("");
        }
        //设置插入位置为第0行
        Address = 0;
    }

    public void paintComponent(Graphics g) {
        //定义一个空的图片流
        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //获取图片流的Graphics
        Graphics2D graphics = bufferedImage.createGraphics();
        //绘制背景图片
//        image = image.getScaledInstance(DefultSet.infBoardWidth, DefultSet.infBoardHeight, Image.SCALE_FAST);
        graphics.drawImage(image, 0, 0,DefultSet.infBoardWidth,DefultSet.infBoardHeight,this);
        //设置字体颜色
        graphics.setColor(Color.white);
        //设置字体
        graphics.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 16));
        //开启字体抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //绘制字体，内容为需要显示的10行文字
        for (int i = 0; i < 10; i++) {
            graphics.drawString(logS[i], 10, 30 + i * 25);
        }
        //刷新图片流至g
        g.drawImage(bufferedImage, 0, 0, null);
    }

    public String AddLog(String s) {
        //记录数据后添加一行
        log.append(s + "\n");
        //原记录小于10行的处理
        if (Address < 10) {
            logS[Address] = s;
            Address++;
        }
        //原记录大于等于10行的处理
        else {
            for (int i = 0; i < 9; i++) {
                logS[i] = logS[i + 1];
            }
            logS[9] = s;
        }
        //刷新界面
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        return new String(log);
    }

    public void Clear() {
        //清空log
        this.log = new StringBuffer();
        //清空显示的10行
        for (int i = 0; i < 10; i++) {
            logS[i] = new String("");
        }
        //清空插入位置
        Address = 0;
    }
}
