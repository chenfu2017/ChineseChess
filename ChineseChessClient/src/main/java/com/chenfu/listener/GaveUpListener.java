package com.chenfu.listener;

import com.chenfu.components.InformationBoard;
import com.chenfu.netty.Client;
import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.Player;
import com.chenfu.view.GameView;
import io.netty.channel.Channel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaveUpListener implements ActionListener {

        /*    LOGIN(1, "用户登录"),
              CHATMSG(2,"聊天消息"),
              WITHDRAW(3,"悔棋"),
              ASKDRAW(4,"求和"),
              GIVEUP(5,"认输"),
              NEWGAME(6,"新游戏"),
              PIECELPOS(7,"棋子信息");*/

    private GameView gameView;

    public GaveUpListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameView.getChessBoard().wait){
            InformationBoard.getInstance().addLog("还未轮到您!");
            return;
        }
        int i = JOptionPane.showConfirmDialog(gameView, "您确定认输吗？", "系统信息", JOptionPane.YES_NO_OPTION);
        if(i == JOptionPane.YES_OPTION){
            if(gameView.status == GameStatusEnum.AI_START.status){
                gameView.lose();
                gameView.status = GameStatusEnum.INIT.status;
                gameView.flag = false;

            }else if(gameView.status == GameStatusEnum.NETWORK_START.status){
                Client instance = Client.getInstance();
                Channel channel = instance.getChannel();
                Player competitor = gameView.getCompetitor();
                competitor.setPassword("ASK");
                DataContent dataContent = new DataContent(competitor);
                dataContent.setAction(5);
                channel.writeAndFlush(dataContent);
                gameView.lose();
            }
        }
    }
}
