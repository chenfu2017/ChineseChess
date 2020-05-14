package com.chenfu;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

public class AudioPlayer extends Thread {
    private Player player;
    private String filename;
    private boolean repeat = false;

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public AudioPlayer(String filename, boolean repeat) {
        this.filename = filename;
        this.repeat = repeat;
    }

    public AudioPlayer(String filename) {
        this.filename = filename;
    }


    //重写run方法
    @Override
    public void run() {
        try {
            do {
                play();
            } while (player.isComplete() && repeat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //播放方法
    public void play() {
        InputStream audioInputStream = Utils.getAudioInputStream(filename);
        try {
            player = new Player(audioInputStream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        player.close();
    }
}
