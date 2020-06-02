package com.chenfu.listener;

import com.chenfu.netty.Client;
import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.Player;
import com.chenfu.view.GameView;
import io.netty.channel.Channel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithDrawListener implements ActionListener {

    private GameView gameView;

    public WithDrawListener(GameView gameView) {
        this.gameView = gameView;
    }

    /*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏"),
    PIECELPOS(7,"棋子信息");*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameView.status== GameStatusEnum.AI_START.status){
            int i = JOptionPane.showConfirmDialog(gameView, "您确定发出悔棋请求吗？", "系统信息", JOptionPane.YES_NO_OPTION);
            if(i == JOptionPane.YES_OPTION){
                gameView.goBackTwice();
            }
        }else if(gameView.status== GameStatusEnum.NETWORK_START.status){
            if(!gameView.getChessBoard().wait){
                gameView.getInformationBoard().addLog("轮到您落子！");
            }else {
                int i = JOptionPane.showConfirmDialog(gameView, "您确定发出悔棋请求吗？", "系统信息", JOptionPane.YES_NO_OPTION);
                if(i == JOptionPane.YES_OPTION){
                    if(gameView.getChessBoard().wait){
                        Client instance = Client.getInstance();
                        Channel channel = instance.getChannel();
                        Player competitor = gameView.getCompetitor();
                        competitor.setPassword("ASK");
                        DataContent dataContent = new DataContent(competitor);
                        dataContent.setAction(3);
                        channel.writeAndFlush(dataContent);
                    }
                }
            }
        }
    }
}
