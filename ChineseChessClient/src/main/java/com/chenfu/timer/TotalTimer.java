package com.chenfu.timer;

import com.chenfu.DefultSet;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TotalTimer {
    private JLabel desLabel;
    private long stepTime;
    private long oldTime;

    public TotalTimer(JLabel jLabel) {
        desLabel = jLabel;
        stepTime = DefultSet.totalTime;
    }

    public void start() {
        oldTime = System.currentTimeMillis();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DecimalFormat decimalFormat = new DecimalFormat("00");
//                long midTime =stepTime-(System.currentTimeMillis() - oldTime)/1000;
                long midTime = stepTime;
                long mm = midTime / 60 % 60;
                long ss = midTime % 60;
                desLabel.setText("局时 " + decimalFormat.format(mm) + ":" + decimalFormat.format(ss)+"");
            }
        },0,1000);
    }

    public void reStart(){
        oldTime = System.currentTimeMillis();
    }
}
