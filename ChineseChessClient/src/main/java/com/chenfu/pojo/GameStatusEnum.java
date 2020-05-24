package com.chenfu.pojo;

public enum GameStatusEnum {


    INIT(1, "初始状态"),
    AI_START(3, "人机已开始状态"),
    NETWORK_NO_LOGIN(4,"网络未登录"),
    NETWORK_LOGIN(5,"网络已登录"),
    NETWORK_START(6, "网络匹配成功");


    public final Integer status;
    public final String content;

    GameStatusEnum(Integer status, String content) {
        this.status = status;
        this.content = content;
    }
}
