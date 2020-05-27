package com.chenfu.listener;

import com.chenfu.chess.ChessBoard;
import com.chenfu.inform.InformationBoard;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameLister implements ActionListener {

    private GameView gameView;
    private ChessBoard chessBoard;
    private InformationBoard informationBoard;

    public NewGameLister(GameView gameView, ChessBoard chessBoard, InformationBoard informationBoard) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.informationBoard = informationBoard;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int status = gameView.status;
        switch (status) {
            case 1:informationBoard.AddLog("请选择游戏模式！");break;
            case 2:
            case 3:
                gameView.newAIGame();
                informationBoard.AddLog("重新开始！");break;
            case 4:
                informationBoard.AddLog("please login!");break;
        }
    }
}
