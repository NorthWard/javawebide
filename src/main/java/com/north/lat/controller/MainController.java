package com.north.lat.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequestMapping("api")
public class MainController {
    private static Random random = new Random();
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("getHints")
    @ResponseBody
    public List<String> getHints(String word){
        List<String> list = new ArrayList<>();
        System.out.println("word = [" + word + "]");
        list.add(random.nextInt() + "");
        list.add(random.nextInt() + "");
        list.add(random.nextInt() + "");
        return list;
    }
}
