package com.chenfu.pojo;

public enum GameStatusEnum {


    INIT(1, "初始状态"),
    AI_NO_START(2, "人机未开始状态"),
    AI_START(3, "人机已开始状态"),
    NETWORK_MODE(4,"网络未登录"),
    NETWORK_START(5, "网络匹配成功");


    public final Integer status;
    public final String content;

    GameStatusEnum(Integer status, String content) {
        this.status = status;
        this.content = content;
    }
}
