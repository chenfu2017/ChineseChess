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

public class AskdrawListener implements ActionListener {

    /*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏"),
    PIECELPOS(7,"棋子信息");*/

    private GameView gameView;

    public AskdrawListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameView.getChessBoard().wait){
            InformationBoard.getInstance().addLog("还未轮到您!");
            return;
        }
        if (gameView.status == GameStatusEnum.AI_START.status) {
            JOptionPane.showMessageDialog(gameView, "电脑拒绝了您的求和请求！");
        } else if (gameView.status == GameStatusEnum.NETWORK_START.status) {
            int i = JOptionPane.showConfirmDialog(gameView, "您确定发出求和请求吗？", "系统信息", JOptionPane.YES_NO_OPTION);
            if(i == JOptionPane.YES_OPTION){
                Client instance = Client.getInstance();
                Channel channel = instance.getChannel();
                Player competitor = gameView.getCompetitor();
                competitor.setPassword("ASK");
                DataContent dataContent = new DataContent(competitor);
                dataContent.setAction(4);
                channel.writeAndFlush(dataContent);
            }
        } else {
            gameView.getInformationBoard().addLog("游戏未开始！");
        }

    }

}
