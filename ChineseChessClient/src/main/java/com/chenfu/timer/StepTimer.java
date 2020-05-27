package com.chenfu.timer;


import com.chenfu.DefaultSet;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class StepTimer {
    private JLabel desLabel;
    private long stepTime;
    private long oldTime;

    public StepTimer(JLabel jLabel) {
        desLabel = jLabel;
        stepTime = DefaultSet.stepTime;
    }

    public void start() {
        oldTime = System.currentTimeMillis();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                long midTime =stepTime-(System.currentTimeMillis() - oldTime)/1000;
                long mm = midTime / 60 % 60;
                long ss = midTime % 60;
                desLabel.setText("步时 " + decimalFormat.format(mm) + ":" + decimalFormat.format(ss)+"");
            }
        },0,1000);
    }

    public void reStart(){
        oldTime = System.currentTimeMillis();
    }

}
