package com.chenfu.netty;

import com.chenfu.pojo.DataContent;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.Player;
import com.chenfu.view.GameView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class DataContentHandler extends SimpleChannelInboundHandler<DataContent> {

    private GameView gameView;

    public DataContentHandler(GameView gameView) {
        this.gameView = gameView;
    }


/*   INIT(1, "初始状态"),
    AI_NO_START(2, "人机模式就绪"),
    AI_START(3, "人机已开始状态"),
    NETWORK_MODE(4,"网络模式就绪"),
    NETWORK_START(5, "网络匹配成功");*/


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataContent dataContent) throws Exception {
        if(dataContent ==null)
            return;
        int action = dataContent.getAction();
        switch (action){
            case 5:
                Player competitor =(Player) dataContent.getObject();
                gameView.setCompetitor(competitor);
                gameView.status = GameStatusEnum.NETWORK_START.status;
                gameView.getInformationBoard().AddLog("匹配成功！");
                System.out.println(competitor);
        }
    }
}
