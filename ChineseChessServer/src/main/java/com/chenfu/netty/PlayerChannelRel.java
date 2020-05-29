package com.chenfu.netty;


import com.chenfu.pojo.Player;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.Set;
public class PlayerChannelRel {

    private final static Map<String,Channel> playerManager = Maps.newConcurrentMap();

    public static Channel getChannel(String username) {
        return playerManager.get(username);
    }

    public static void put(String username, Channel channel) {
        playerManager.put(username, channel);
    }

    public static boolean isLogin(Player player){
        return playerManager.containsKey(player);
    }

    public static Set<String> getOnlinePlayers(){
        return playerManager.keySet();
    }

    public static String removeByChannel(Channel channel){
        for (Map.Entry<String,Channel> entry: playerManager.entrySet()) {
            if (entry.getValue() == channel) {
                String username = entry.getKey();
                playerManager.remove(username);
                return entry.getKey();
            }
        }
        return null;
    }

}
