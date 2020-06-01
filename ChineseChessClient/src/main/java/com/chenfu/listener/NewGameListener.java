package com.chenfu.listener;

import com.chenfu.pojo.ChessBoard;
import com.chenfu.components.InformationBoard;
import com.chenfu.netty.Client;
import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.MsgActionEnum;
import com.chenfu.pojo.Player;
import com.chenfu.view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameListener implements ActionListener {

    private GameView gameView;
    private ChessBoard chessBoard;
    private InformationBoard informationBoard;

    public NewGameListener(GameView gameView, ChessBoard chessBoard, InformationBoard informationBoard) {
        this.gameView = gameView;
        this.chessBoard = chessBoard;
        this.informationBoard = informationBoard;
    }


/*    INIT(1, "初始状态"),
    AI_NO_START(2, "人机模式就绪"),
    AI_START(3, "人机已开始状态"),
    NETWORK_MODE(4,"网络模式就绪"),
    NETWORK_START(5, "网络匹配成功");*/
    @Override
    public void actionPerformed(ActionEvent e) {
        int status = gameView.status;
        switch (status) {
            case 1:informationBoard.addLog("请选择游戏模式！");break;
            case 2:
            case 3:
                gameView.newGame('r');
                informationBoard.addLog("重新开始！");break;
            case 4:
                informationBoard.addLog("匹配中,请稍后...");
                Client instance = Client.getInstance();
                DataContent dataContent = new DataContent();
                dataContent.setAction(MsgActionEnum.NEWGAME.type);
                dataContent.setObject(gameView.getPlayer());
                instance.getChannel().writeAndFlush(dataContent);
                break;
        }
    }
}
