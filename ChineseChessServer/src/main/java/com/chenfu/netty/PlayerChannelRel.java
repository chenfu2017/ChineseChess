package com.chenfu.netty;


import com.chenfu.pojo.Player;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import java.util.Map;
import java.util.Set;
public class PlayerChannelRel {

    private final static Map<Player,Channel> playerManager = Maps.newConcurrentMap();

    public static Channel getChannel(Player player) {
        return playerManager.get(player);
    }

    public static void put(Player player, Channel channel) {
        playerManager.put(player, channel);
    }

    public static boolean isLogin(Player player){
        return playerManager.containsKey(player);
    }

    public static Set<Player> getOnlinePlayers(){
        return playerManager.keySet();
    }

    public static Player removeByChannel(Channel channel){
        for (Map.Entry<Player,Channel> entry: playerManager.entrySet()) {
            if (entry.getValue() == channel) {
                Player player = entry.getKey();
                playerManager.remove(player);
                return entry.getKey();
            }
        }
        return null;
    }

}
