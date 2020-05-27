package com.chenfu.netty;

import com.chenfu.view.GameView;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;
    private GameView gameView;

    private static class SingletionClient {
        static final Client instance = new Client();
    }

    public static Client getInstance() {
        return SingletionClient.instance;
    }

    private Client()  {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitialzer());
        try {
            channel =bootstrap.connect("127.0.0.1",8888).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("netty client 启动完毕...");
    }

    public  Channel getChannel() {
        return channel;
    }
    public void shutdown(){
        group.shutdownGracefully();
    }
}
