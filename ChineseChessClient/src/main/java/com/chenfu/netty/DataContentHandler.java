package com.chenfu.netty;

import com.chenfu.pojo.DataContent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class DataContentHandler extends SimpleChannelInboundHandler<DataContent> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DataContent dataContent) throws Exception {
        System.out.println(dataContent);
    }
}
