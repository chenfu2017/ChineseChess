package com.chenfu.netty;

import com.chenfu.components.InformationBoard;
import com.chenfu.pojo.JSONResult;
import com.chenfu.pojo.Player;
import com.chenfu.view.GameView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonResultHander extends SimpleChannelInboundHandler<JSONResult> {

    InformationBoard informationBoard= InformationBoard.getInstance();

    private GameView gameView;

    public JsonResultHander(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JSONResult jsonResult) throws Exception {
        String msg = jsonResult.getMsg();
        informationBoard.addLog(msg);
        if(jsonResult.getStatus()==200){
            Player player =(Player)jsonResult.getData();
            gameView.setPlayer(player);
            gameView.getLoginMenu().setText("登录成功");
            informationBoard.addLog("欢迎你,"+player.getUsername()+"!");
        }
    }
}
