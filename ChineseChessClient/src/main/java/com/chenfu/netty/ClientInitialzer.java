package com.chenfu.netty;

import com.chenfu.view.GameView;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ClientInitialzer extends ChannelInitializer<SocketChannel> {

    private GameView gameView;

    public ClientInitialzer(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ObjectDecoder(1024*1024, ClassResolvers.cacheDisabled(null)));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new DataContentHandler(gameView));
        pipeline.addLast(new JsonResultHander(gameView));
    }
}
