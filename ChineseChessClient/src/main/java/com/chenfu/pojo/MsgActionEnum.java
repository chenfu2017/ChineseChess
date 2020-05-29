package com.chenfu.pojo;

import lombok.Getter;

@Getter
public enum MsgActionEnum {

/*    LOGIN(1, "用户登录"),
    CHATMSG(2,"聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5,"认输"),
    NEWGAME(6,"新游戏"),
    PIECELPOS(7,"棋子信息");*/


    LOGIN(1, "用户登录"),
    CHATMSG(2, "聊天消息"),
    WITHDRAW(3,"悔棋"),
    ASKDRAW(4,"求和"),
    GIVEUP(5, "认输"),
    NEWGAME(6, "新游戏"),
    PIECELPOS(7,"棋子信息");

    public final Integer type;
    public final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }
}
