package com.chenfu;

import com.chenfu.adapter.ExitAdapter;
import com.chenfu.adapter.LoginAdapter;
import com.chenfu.adapter.MusicControlAdapter;
import com.chenfu.adapter.PieceClickAdapter;
import com.chenfu.button.DiyButton;
import com.chenfu.listener.AskdrawLister;
import com.chenfu.timer.StepTimer;
import com.chenfu.timer.TotalTimer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChessFrame extends JFrame {

    private JPanel contentPanel;
    private JPanel jPanel;

    private static InformationBoard InfBoard;
    private static ChessBoard chessBoard;
    private AudioPlayer audioPlayer;
    private StepTimer stepTimer;
    private TotalTimer totalTimer;


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
        JLabel backGround = new JLabel("");
        backGround.setIcon(Utils.getImageIcon("bg.jpg"));
        backGround.setBounds(0, 0, 1366, 768);
        //添加背景图片的关键语句
        this.getLayeredPane().add(backGround, new Integer(Integer.MIN_VALUE));

//        //添加作者信息
//        JLabel authorInf = new JLabel("");
//        authorInf.setIcon(Utils.getImageIcon("background.png"));
//        //设置作者信息位置
//        ImageIcon imageIcon = Utils.getImageIcon("information.png");
//        authorInf.setBounds(10, 500, imageIcon.getIconWidth(), imageIcon.getIconHeight());
//        contentPanel.add(authorInf);


        //添加菜单

        Font font = new Font("宋体", Font.BOLD, 40);
        Color color = Color.BLUE;
        JLabel menu1 = new JLabel("用户登录");
        menu1.setFont(font);
        menu1.setForeground(color);
        menu1.setBounds(DefultSet.menuX, DefultSet.menuY, DefultSet.menuWidth, DefultSet.menuHeight);
        menu1.addMouseListener(new LoginAdapter(this,menu1));
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
        jPanel = new JPanel();
        jPanel.setBounds(0, 0, 1366, 768);
        jPanel.setOpaque(false);
        jPanel.setVisible(true);
        jPanel.setLayout(null);

        //把Panel添加进ContentPanel
        contentPanel.add(jPanel);

        //对Pane1添加Canvas来绘制棋盘
        chessBoard = new ChessBoard();
        //设置Canvas位置和大小
        chessBoard.setBounds(DefultSet.canvasPosX, DefultSet.canvasPosY, 504, 571);
        chessBoard.addMouseListener(new PieceClickAdapter(chessBoard,this));
        jPanel.add(chessBoard);

        //对Pane1添加信息栏
        InfBoard = new InformationBoard();
        InfBoard.setBounds(DefultSet.infBoardX,DefultSet.infBoardY,DefultSet.infBoardWidth,DefultSet.infBoardHeight);
        jPanel.add(InfBoard);

        //添加时间标签
        JLabel totaltimerLabel = new JLabel();
        totaltimerLabel.setBounds(DefultSet.timerLabelX,DefultSet.timerLabelY,DefultSet.timerLabelWidth,DefultSet.timerLabelHeight);
        totaltimerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        totaltimerLabel.setForeground(Color.RED);
        jPanel.add(totaltimerLabel);
        totalTimer = new TotalTimer(totaltimerLabel);
        totalTimer.start();

        JLabel timerLabel = new JLabel();
        timerLabel.setBounds(DefultSet.timerLabelX,DefultSet.timerLabelY+DefultSet.timerLabelP,DefultSet.timerLabelWidth,DefultSet.timerLabelHeight);
        timerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        timerLabel.setForeground(Color.RED);
        jPanel.add(timerLabel);
        stepTimer = new StepTimer(timerLabel);
        stepTimer.start();

        //添加按钮
        DiyButton diyButton1 = new DiyButton("新对局",DefultSet.buttonX,DefultSet.buttonY);
        jPanel.add(diyButton1);

        DiyButton diyButton2 = new DiyButton("悔棋",DefultSet.buttonX+DefultSet.buttonP,DefultSet.buttonY);
        jPanel.add(diyButton2);

        DiyButton diyButton3 = new DiyButton("求和",DefultSet.buttonX+DefultSet.buttonP*2,DefultSet.buttonY);
        diyButton3.addActionListener(new AskdrawLister());
        jPanel.add(diyButton3);

        DiyButton diyButton4 = new DiyButton("认输",DefultSet.buttonX+DefultSet.buttonP*3,DefultSet.buttonY);
        jPanel.add(diyButton4);


        InfBoard.AddLog("对局开始！");
        //播放背景音乐
//        audioPlayer.play();
    }

    public StepTimer getStepTimer() {
        return stepTimer;
    }
}
