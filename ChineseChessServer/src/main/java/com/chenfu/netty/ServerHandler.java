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
import jdk.nashorn.internal.ir.CallNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<DataContent> {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("客户端被移除，channelId为:{}", channel.id().asShortText());
        clients.remove(channel);
        String username = PlayerChannelRel.removeByChannel(channel);
        Player player = new Player();
        player.setUsername(username);
        CounterpartManager.removenoMatchPlayer(player);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception:{}", cause.getMessage());
        Channel channel = ctx.channel();
        clients.remove(channel);
        String username = PlayerChannelRel.removeByChannel(channel);
        Player player = new Player();
        player.setUsername(username);
        CounterpartManager.removenoMatchPlayer(player);
        channel.close();
    }

    /*    LOGIN(1, "用户登录"),
          CHATMSG(2,"聊天消息"),
          WITHDRAW(3,"悔棋"),
          ASKDRAW(4,"求和"),
          GIVEUP(5,"认输"),
          NEWGAME(6,"新游戏");*/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataContent dataContent) throws Exception {
        log.info("msg:{}", dataContent);
        Channel currentChannel = channelHandlerContext.channel();
        int action = dataContent.getAction();
        Object object = dataContent.getObject();
        Player player = null;
        Player competitor = null;
        Channel channel = null;
        JSONResult jsonResult = null;
        String username = null;
        if (object instanceof Player) {
            player = (Player) object;
        }
        switch (action) {
            case 1:
                PlayerService playerService = SpringUtils.getBean(PlayerService.class);
                jsonResult = playerService.login(player.getUsername(), player.getPassword());
                if (jsonResult.isOK()) {
                    Player resultPlayer = (Player) jsonResult.getData();
                    PlayerChannelRel.put(resultPlayer.getUsername(), currentChannel);
                    CounterpartManager.addNoMatchPlayer(resultPlayer);
                    clients.add(currentChannel);
                }
                currentChannel.writeAndFlush(jsonResult);
                break;
            case 2:
                ChatMsg chatMsg = (ChatMsg)object;
                channel = PlayerChannelRel.getChannel(chatMsg.getUsername());
                channel.writeAndFlush(dataContent);
                break;
            case 3:
                username = player.getUsername();
                channel = PlayerChannelRel.getChannel(username);
                channel.writeAndFlush(dataContent);
                break;
            case 4:
                username = player.getUsername();
                if(player.getPassword().equals("AGREE")){
                    competitor = CounterpartManager.getCompetitor(player);
                    CounterpartManager.addNoMatchPlayer(competitor);
                    CounterpartManager.addNoMatchPlayer(player);
                }
                channel = PlayerChannelRel.getChannel(username);
                channel.writeAndFlush(dataContent);
                break;
            case 5:
                competitor = CounterpartManager.getCompetitor(player);
                CounterpartManager.addNoMatchPlayer(competitor);
                CounterpartManager.addNoMatchPlayer(player);
                username = player.getUsername();
                channel = PlayerChannelRel.getChannel(username);
                channel.writeAndFlush(dataContent);
                break;
            case 6:
                competitor = CounterpartManager.match(player);
                if (competitor == null) {
                    jsonResult = new JSONResult();
                    jsonResult.setMsg("当前在线人数不足！");
                    jsonResult.setStatus(500);
                    currentChannel.writeAndFlush(jsonResult);
                } else {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        player.setPassword("b");
                        competitor.setPassword("r");
                    } else {
                        player.setPassword("r");
                        competitor.setPassword("b");
                    }
                    dataContent.setAction(MsgActionEnum.NEWGAME.type);
                    dataContent.setObject(competitor);
                    currentChannel.writeAndFlush(dataContent);
                    channel = PlayerChannelRel.getChannel(competitor.getUsername());
                    dataContent.setObject(player);
                    channel.writeAndFlush(dataContent);
                }
                break;
            case 7:
                PieceMsg pieceMsg = (PieceMsg) dataContent.getObject();
                channel = PlayerChannelRel.getChannel(pieceMsg.getUsername());
                channel.writeAndFlush(dataContent);
                break;
        }
    }
}
