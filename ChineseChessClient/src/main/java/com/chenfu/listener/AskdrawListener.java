package com.chenfu.listener;

import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskdrawListener implements ActionListener {

    private GameView gameView;

    public AskdrawListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameView.status== GameStatusEnum.AI_START.status){
            JOptionPane.showMessageDialog(gameView,"电脑拒绝了您的求和请求！");
        }else if(gameView.status== GameStatusEnum.NETWORK_START.status){
            JOptionPane.showConfirmDialog(null, "您确定发出求和请求吗？", "系统信息", JOptionPane.YES_NO_OPTION); }
        }

}
