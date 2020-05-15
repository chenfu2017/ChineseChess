package com.chenfu.adapter;

import com.chenfu.AudioPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicControlAdapter extends MouseAdapter {

    private AudioPlayer audioPlayer;
    private JLabel jLabel;
    private boolean playing = true;
    private Color foreground;


    public MusicControlAdapter(AudioPlayer audioPlayer, JLabel jLabel) {
        this.audioPlayer = audioPlayer;
        this.jLabel = jLabel;
        this.foreground = jLabel.getForeground();
    }

    public MusicControlAdapter(AudioPlayer audioPlayer, JLabel jLabel, boolean playing) {
        this.audioPlayer = audioPlayer;
        this.jLabel = jLabel;
        this.playing = playing;
        this.foreground = jLabel.getForeground();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        jLabel.setForeground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        jLabel.setForeground(foreground);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (playing) {
            audioPlayer.stop();
            jLabel.setText("开启音乐");
        }else {
            audioPlayer.play();
            jLabel.setText("关闭音乐");
        }
        playing = !playing;
    }
}
