package com.north.lat.controller;

import com.alibaba.fastjson.JSON;
import com.north.lat.autocomplete.model.AutoCompleteCondition;
import com.north.lat.autocomplete.model.TipsType;
import com.north.lat.autocomplete.service.AutoCompleteService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author laihaohua
 */
@Component
@RequestMapping("api")
public class MainController {
/*    @Resource(name = "defaultAutoCompleteService")
    private AutoCompleteService defaultAutoCompleteService;*/
    @RequestMapping("index")
    public String index(){
        return "index";
    }

   /* @RequestMapping("getHints")
    @ResponseBody
    public String getHints(String word){
        AutoCompleteCondition.AutoCompleteConditionBuilder builder = new AutoCompleteCondition.AutoCompleteConditionBuilder(TipsType.NEW);
        builder.setWord(word);
        return JSON.toJSONString(defaultAutoCompleteService.nextTips(builder.build()));
    }*/
}
