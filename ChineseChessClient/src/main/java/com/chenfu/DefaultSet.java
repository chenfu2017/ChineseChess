package com.chenfu;

public class DefaultSet {

    public static int frameWidth = 1000;
    public static int frameHeight = 700;
    public static int DEPTH = 3;

    //棋盘位置
    public static int canvasPosX = 210;
    public static int canvasPosY = 30;
    public static int canvasPosWidth = 480;
    public static int canvasPosHeight = 550;

    //棋子偏移值
    public static int SX_OFFSET = canvasPosX+4;
    public static int SY_OFFSET = canvasPosY+4;
    public static int SY_COE = 53;
    public static int SX_COE = 52;

    //帅将提示图标
    public static int playerIconX = canvasPosX/2 -20;
    public static int playerIconY = 500;

    //棋子大小
    public static int pieceSize = 52;

    //游戏菜单栏
    public static int menuX = 20;
    public static int menuY = canvasPosY;
    public static int menuP = 60;
    public static int menuWidth = 200;
    public static int menuHeight = 60;

    //系统通知栏
    public static int infBoardX = canvasPosX + canvasPosWidth+10;
    public static int infBoardY = 250;
    public static int infBoardWidth = frameWidth - canvasPosX -canvasPosWidth-25;
    public static int infBoardHeight = 320;

    //步时 局时计时器
    public static int timerLabelX = 760;
    public static int timerLabelY = 50;
    public static int timerLabelP = 40;
    public static int timerLabelWidth = 328;
    public static int timerLabelHeight = 30;

    //步时 局时时间
    public static long stepTime = 90;
    public static long totalTime = 600;

    //棋局菜单栏
    public static int buttonX = canvasPosX+10;
    public static int buttonY = canvasPosY+canvasPosHeight+10;
    public static int buttonP = 120;

    //输入框
    public static int textFieldX = infBoardX +5;
    public static int textFieleY = infBoardY + infBoardHeight;
    public static int textFieldWidth = 220;
    public static int textFieleHeight = 28;

    //发送按钮
    public static int sendButtonX = infBoardX + infBoardWidth -60;
    public static int sendButtonY = infBoardY + infBoardHeight;

}
