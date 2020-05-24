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

    @Override
    public void actionPerformed(ActionEvent e) {
        int status = chessFrame.getStatus();
        switch (status) {
            case 0:informationBoard.AddLog("请选择游戏模式！");break;
            case 1:
                chessFrame.getStepTimer().start();
                chessFrame.getTotalTimer().start();
                informationBoard.AddLog("对局开始！");break;
            case 2:
                informationBoard.AddLog("匹配对局中...");break;
        }
    }
}
