package com.chenfu;

import com.chenfu.pojo.Player;
import com.chenfu.service.PlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class Dome {

    @Autowired
    private PlayerService playerService;
    @Test
    public void test() {
        List<Player> all = playerService.getAll();
        System.out.println(all);
    }
}
