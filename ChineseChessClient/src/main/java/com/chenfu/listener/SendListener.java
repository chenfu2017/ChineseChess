package com.chenfu.listener;

import com.chenfu.components.InformationBoard;
import com.chenfu.netty.Client;
import com.chenfu.pojo.ChatMsg;
import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.MsgActionEnum;
import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendListener implements ActionListener {
    private GameView gameView;
    private JTextField jTextField;


    public SendListener(GameView gameView,JTextField jTextField) {
        this.jTextField = jTextField;
        this.gameView = gameView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InformationBoard informationBoard = InformationBoard.getInstance();
        if(gameView.status == GameStatusEnum.NETWORK_START.status){
            Client client = Client.getInstance();
            String competitorUsername = gameView.getCompetitor().getUsername();
            ChatMsg chatMsg = new ChatMsg(competitorUsername, jTextField.getText());
            DataContent dataContent = new DataContent(chatMsg);
            dataContent.setAction(MsgActionEnum.CHATMSG.type);
            client.getChannel().writeAndFlush(dataContent);
            informationBoard.addLog("你对"+competitorUsername+"说:"+jTextField.getText());
            jTextField.setText("");
        }else {
            informationBoard.addLog("当前不能发送消息！");
        }

    }
}
