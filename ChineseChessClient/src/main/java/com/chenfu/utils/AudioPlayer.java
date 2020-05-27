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
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void stop(){
        mediaPlayer.stop();
    }

}
