package com.chenfu.controller;

import com.chenfu.netty.CounterpartManager;
import com.chenfu.pojo.Player;
import com.chenfu.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping("/showOnline")
    private Set<Player> getOnlinePlayers() {
        return playerService.getOnlinePlayers();
    }

    @RequestMapping("/initNoMatchPlayer")
    private void clearOnlinePlayers(){
        CounterpartManager.initNoMatchPlayer();
    }

}
