package com.chenfu.controller;

import com.chenfu.netty.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    @RequestMapping("/baidumap")
    public String getMap(){
        return "baidumap";
    }

    @RequestMapping("/newinstance")
    @ResponseBody
    public String newInstance(){
        Server instance = Server.getInstance();
        instance.shutdown();
        return "newinstance success!";
    }
}
