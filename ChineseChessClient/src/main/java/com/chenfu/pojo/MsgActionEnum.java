package com.chenfu.pojo;

import lombok.Getter;

@Getter
public enum MsgActionEnum {

/*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏");*/


    POLICE_COORDIANATE(1, "交警坐标"),
    DRIVER_COORDIANATE(2, "司机坐标"),
    POLICE_CONNECT(3,"交警连接"),
    CLIENT_CONNECT(4,"客户端连接"),
    POLICE_COORDIANATE_TO_PC(5, "发送交警坐标到PC端"),
    DRIVER_COORDIANATE_TO_PC(6, "发送司机坐标到PC端"),
    MESSION(7,"新的任务");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }
}
