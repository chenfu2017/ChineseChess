package com.chenfu.netty;

import com.chenfu.inform.InformationBoard;
import com.chenfu.pojo.JSONResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JsonResultHander extends SimpleChannelInboundHandler<JSONResult> {

    InformationBoard informationBoard= InformationBoard.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JSONResult jsonResult) throws Exception {
        informationBoard.AddLog(jsonResult.getMsg());
    }
}
