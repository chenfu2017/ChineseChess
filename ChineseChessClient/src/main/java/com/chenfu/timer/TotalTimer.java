package com.chenfu.timer;

import com.chenfu.DefaultSet;
import com.chenfu.view.GameView;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TotalTimer {
    private JLabel desLabel;
    private long totalTime;
    private long oldTime;
    private Timer timer;
    private GameView gameView;

    public TotalTimer(GameView gameView,JLabel jLabel) {
        this.gameView = gameView;
        desLabel = jLabel;
        totalTime = DefaultSet.totalTime;
    }

    public void start() {
        oldTime = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                long midTime = totalTime -(System.currentTimeMillis() - oldTime)/1000;
                if(midTime<0){
                    gameView.showWinner(gameView.getCompetitor().getPassword().charAt(0));
                    cancel();
                }
                long mm = midTime / 60 % 60;
                long ss = midTime % 60;
                desLabel.setText("局时 " + decimalFormat.format(mm) + ":" + decimalFormat.format(ss)+"");
            }
        },0,1000);
    }

    public void reStart(){
        oldTime = System.currentTimeMillis();
    }

    public void stop(){
        timer.cancel();
    }

}
