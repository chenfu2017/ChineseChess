package com.chenfu.service;


import com.chenfu.pojo.JSONResult;
import com.chenfu.pojo.Player;

import java.util.List;
import java.util.Set;

public interface PlayerService {
    List<Player> getAll();

    JSONResult login(String username, String password);

    Set<Player> getOnlinePlayers();
}
