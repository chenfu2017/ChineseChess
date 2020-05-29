package com.chenfu.service.impl;

import com.chenfu.mapper.PlayerMapper;
import com.chenfu.netty.PlayerChannelRel;
import com.chenfu.pojo.JSONResult;
import com.chenfu.pojo.Player;
import com.chenfu.service.PlayerService;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;
    @Override
    public List<Player> getAll() {
        List<Player> players = playerMapper.selectAll();
        return players;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerMapper.selectByPrimaryKey(username);
    }

    @Override
    public JSONResult login(String username, String password) {
        if(username==null ||password==null ||username.equals("")||password.equals("")){
            return JSONResult.errorMsg("name or password can not be empty.");
        }

        Player player = playerMapper.selectByPrimaryKey(username);
        if(player == null) {
            player = new Player();
            player.setUsername(username);
            player.setPassword(password);
            player.setLevel(0);
            playerMapper.insertSelective(player);
            JSONResult jsonResult = JSONResult.ok();
            jsonResult.setMsg("regist successfully");
            return jsonResult;
        } else {
            String cpassword = player.getPassword();
            if (password.equals(cpassword)){
                JSONResult jsonResult = JSONResult.ok();
                jsonResult.setData(player);
                jsonResult.setMsg("login successfully");
                return jsonResult;
            }else {
                return JSONResult.errorMsg("incorrect password!");
            }
        }
    }

    @Override
    public Set<String> getOnlinePlayers() {
        return PlayerChannelRel.getOnlinePlayers();
    }
}
