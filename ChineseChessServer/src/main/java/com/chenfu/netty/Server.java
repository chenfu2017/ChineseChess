package com.chenfu.netty;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class Server {

	private static class SingletionServer {
		static final Server instance = new Server();
	}
	
	public static Server getInstance() {
		return SingletionServer.instance;
	}
	
	private EventLoopGroup mainGroup;
	private EventLoopGroup subGroup;
	private ServerBootstrap server;
	private ChannelFuture future;
	
	public Server() {
		mainGroup = new NioEventLoopGroup();
		subGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitialzer());
	}
	
	public void start() {
		this.future = server.bind(8888);
		System.err.println("netty server 启动完毕...");
	}

	public void shutdown(){
        mainGroup.shutdownGracefully();
        subGroup.shutdownGracefully();
    }
}
