package com.chenfu.listener;

import com.chenfu.ChessFrame;
import com.chenfu.InformationBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameLister implements ActionListener {

    private ChessFrame chessFrame;
    private InformationBoard informationBoard;

    public NewGameLister(ChessFrame chessFrame,InformationBoard informationBoard) {
        this.chessFrame = chessFrame;
        this.informationBoard = informationBoard;
    }

    /*    INIT(1, "初始状态"),
    AI_NO_START(2, "人机未开始状态"),
    AI_START(3, "人机已开始状态"),
    NETWORK_NO_LOGIN(4,"网络未登录"),
    NETWORK_LOGIN(5,"网络已登录"),
    NETWORK_START(6, "网络匹配成功");
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        int status = chessFrame.status;
        switch (status) {
            case 1:informationBoard.AddLog("请选择游戏模式！");break;
            case 3:
                chessFrame.getChessBoard().initBoard();
                chessFrame.getStepTimer().reStart();
                chessFrame.getTotalTimer().reStart();
                chessFrame.getChessBoard().setPoint(null);
                chessFrame.repaint();
                informationBoard.AddLog("重新开始！");break;
            case 4:
                informationBoard.AddLog("please login!");break;
        }
    }
}
