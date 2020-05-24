package com.chenfu.listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AskdrawLister implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showConfirmDialog(null, "您确定发出求和请求吗？", "系统信息", JOptionPane.YES_NO_OPTION);
    }
}
