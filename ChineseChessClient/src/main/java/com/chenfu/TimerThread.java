package com.chenfu;

import javax.swing.JLabel;

public class TimerThread extends Thread {
    private JLabel desLabel;
    private int stepTime = 60;

    public TimerThread(JLabel jLabel) {
        desLabel = jLabel;
    }

    public void run() {
        long oldTime = System.currentTimeMillis();
        desLabel.setText(String.valueOf(stepTime));
        while (!Thread.interrupted()) {
            desLabel.setText(String.valueOf(stepTime - (System.currentTimeMillis() - oldTime) / 1000));
        }
    }
}
