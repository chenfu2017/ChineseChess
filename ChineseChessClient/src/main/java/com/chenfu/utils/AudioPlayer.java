package com.chenfu.utils;

import javax.media.bean.playerbean.MediaPlayer;

public class AudioPlayer {
    private MediaPlayer mediaPlayer;
    private String musicUrl;
    
    public static void main(String[] args){
        String musicUrl = ResourceUtils.getMusicUrl("bgm.wav");
        AudioPlayer audioPlayer = new AudioPlayer(musicUrl,true);
    	audioPlayer.play();
    }

    public AudioPlayer(String filename, boolean IsLoop){
        musicUrl = ResourceUtils.getMusicUrl(filename);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setMediaLocation("file:"+musicUrl);
        mediaPlayer.realize();
        mediaPlayer.setPlaybackLoop(IsLoop);
    }
   
    public void play(){
        try{
	        mediaPlayer.start();
            System.out.println("start:" + musicUrl);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void stop(){
        mediaPlayer.stop();
        System.out.println("stop:" + musicUrl);
    }

}
