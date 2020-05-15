package com.chenfu;

import com.chenfu.adapter.ExitAdapter;
import com.chenfu.adapter.MusicControlAdapter;
import com.chenfu.adapter.PieceClickAdapter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChessFrame extends JFrame {

    private JPanel contentPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    private static InformationBoard InfBoard;
    private static ChessBoard chessBoard;
    private AudioPlayer audioPlayer;


    public ChessFrame() {
        //设置背景音乐
        audioPlayer = new AudioPlayer("bgm.wav", true);
        //设置界面属性
        this.setTitle("chinese chess");
        //设置窗口大小
        this.setBounds(0, 0, 1366, 768);
        //设置窗口不可改变大小
        this.setResizable(false);
        //设置默认关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口居中
        this.setLocationRelativeTo(null);
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        //不使用布局
        contentPanel.setLayout(null);
        //设置ContentPane为透明
        contentPanel.setOpaque(false);
        this.setContentPane(contentPanel);

        //添加背景图片
        JLabel BackGround = new JLabel("");
//        BackGround.setIcon(Utils.getImageIcon("background.png"));
        BackGround.setIcon(Utils.getImageIcon("bg.jpg"));
        BackGround.setBounds(0, 0, 1366, 768);
        //添加背景图片的关键语句
        this.getLayeredPane().add(BackGround, new Integer(Integer.MIN_VALUE));

        //添加作者信息
        JLabel AuthorInf = new JLabel("");
        AuthorInf.setIcon(Utils.getImageIcon("background.png"));
        //设置作者信息位置
        ImageIcon imageIcon = Utils.getImageIcon("information.png");
        AuthorInf.setBounds(10, 500, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        contentPanel.add(AuthorInf);


        //添加菜单

        Font font = new Font("宋体", Font.BOLD, 40);
        Color color = Color.BLUE;
        JLabel menu1 = new JLabel("用户登录");
        menu1.setFont(font);
        menu1.setForeground(color);
        menu1.setBounds(DefultSet.menuX, DefultSet.menuY, DefultSet.menuWidth, DefultSet.menuHeight);
        contentPanel.add(menu1);

        JLabel menu2 = new JLabel("人机对战");
        menu2.setFont(font);
        menu2.setForeground(color);
        menu2.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP, DefultSet.menuWidth, DefultSet.menuHeight);
        contentPanel.add(menu2);

        JLabel menu3 = new JLabel("网络对战");
        menu3.setFont(font);
        menu3.setForeground(color);
        menu3.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*2, DefultSet.menuWidth, DefultSet.menuHeight);
        contentPanel.add(menu3);

        JLabel menu4 = new JLabel("关闭音乐");
        menu4.setFont(font);
        menu4.setForeground(color);
        menu4.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*3, DefultSet.menuWidth, DefultSet.menuHeight);
        menu4.addMouseListener(new MusicControlAdapter(audioPlayer,menu4));
        contentPanel.add(menu4);

        JLabel menu5 = new JLabel("退出游戏");
        menu5.setFont(font);
        menu5.setForeground(color);
        menu5.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*4, DefultSet.menuWidth, DefultSet.menuHeight);
        menu5.addMouseListener(new ExitAdapter(this,menu5));
        contentPanel.add(menu5);

        //初始化4个JPanel
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

        //设置4个JPanel的位置和共同属性
        panel1.setBounds(0, 0, 1366, 768);
        panel1.setOpaque(false);
        panel1.setVisible(true);
        panel1.setLayout(null);
        panel2.setBounds(0, 0, 1366, 768);
        panel2.setOpaque(false);
        panel2.setVisible(false);
        panel2.setLayout(null);
        panel3.setBounds(0, 0, 1366, 768);
        panel3.setOpaque(false);
        panel3.setVisible(false);
        panel3.setLayout(null);
        panel4.setBounds(0, 0, 1366, 768);
        panel4.setOpaque(false);
        panel4.setVisible(false);
        panel4.setLayout(null);

        //把4个Pane添加进ContentPanel
        contentPanel.add(panel1);
        contentPanel.add(panel2);
        contentPanel.add(panel3);
        contentPanel.add(panel4);

        //对Pane1添加Canvas来绘制棋盘
        chessBoard = new ChessBoard();
        //设置Canvas位置和大小
        chessBoard.setBounds(DefultSet.CanvasPosX, DefultSet.CanvasPosY, 504, 571);
        chessBoard.addMouseListener(new PieceClickAdapter(chessBoard));
        panel1.add(chessBoard);

        //对Pane1添加信息栏
        InfBoard = new InformationBoard();
        InfBoard.setBounds(1020, 50, 328, 481);
        panel1.add(InfBoard);

        //添加时间标签
        JLabel TimerLabel = new JLabel();
        TimerLabel.setBounds(1030, 570, 100, 50);
        TimerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        panel1.add(TimerLabel);
        TimerThread MyTimerThread = new TimerThread(TimerLabel);
        MyTimerThread.start();

        InfBoard.AddLog("红方执子");
        audioPlayer.play();
    }

}
