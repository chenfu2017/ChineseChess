package com.chenfu.utils;

import javax.media.bean.playerbean.MediaPlayer;

public class AudioPlayer {
    private final MediaPlayer mediaPlayer;
    private final String musicUrl;

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
