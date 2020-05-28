package com.chenfu.netty;

import com.chenfu.pojo.Player;
import com.google.common.collect.Maps;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CounterpartManager {


    private final static Set<Player> noMatchPlayer = new HashSet<>();
    private final static Map<Player,Player> counterpartManager = Maps.newConcurrentMap();

    public static void addNoMatchPlayer(Player player){
        noMatchPlayer.add(player);
    }

    public static void removenoMatchPlayer(Player player){
        noMatchPlayer.remove(player);
    }

    public static Player getCompetitor(Player player){
        return counterpartManager.get(player);
    }



    public static Player match(Player player){
        if(noMatchPlayer.size()<2){
            return null;
        }
        removenoMatchPlayer(player);
        int level = player.getLevel();
        int min = Integer.MAX_VALUE;
        Player competitor = null;
        for(Player p:noMatchPlayer){
            int temp = p.getLevel();
            int abs = Math.abs(level - temp);
            if(abs<min){
                competitor = p;
                min = abs;
            }
        }
        removenoMatchPlayer(competitor);
        counterpartManager.put(player,competitor);
        counterpartManager.put(competitor,player);
        return competitor;
    }
}
