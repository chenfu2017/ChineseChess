package com.chenfu.netty;

import com.chenfu.utils.SpringUtils;
import com.chenfu.pojo.*;
import com.chenfu.service.PlayerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<DataContent> {

    public static ChannelGroup clients= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel =ctx.channel();
        log.info("客户端被移除，channelId为:{}",channel.id().asShortText());
        clients.remove(channel);
        PlayerChannelRel.removeByChannel(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception:{}",cause.getMessage());
        Channel channel =ctx.channel();
        clients.remove(channel);
        PlayerChannelRel.removeByChannel(channel);
        channel.close();
    }
    /*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏");*/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,DataContent dataContent) throws Exception {
        log.info("msg:{}",dataContent);
        Channel currentChannel = channelHandlerContext.channel();
        int action = dataContent.getAction();
        Object object = dataContent.getObject();
        switch (action){
            case 1:Player player = (Player)object;
                PlayerService playerService = SpringUtils.getBean(PlayerService.class);
                JSONResult jsonResult = playerService.login(player.getUsername(), player.getPassword());
                if(jsonResult.isOK()){
                    Player resultPlayer =(Player) jsonResult.getData();
                    PlayerChannelRel.put(resultPlayer,currentChannel);
                    clients.add(currentChannel);
                }
                currentChannel.writeAndFlush(jsonResult);
        }
    }
}
