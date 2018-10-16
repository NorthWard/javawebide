package com.north.lat.autocomplete.service.impl;

import com.north.lat.autocomplete.model.AutoCompleteTipItem;
import com.north.lat.autocomplete.service.AutoCompleteService;

import java.util.ArrayList;
import java.util.List;

public class JavaParserAutoCompleteServiceImpl implements AutoCompleteService {
    @Override
    public List<AutoCompleteTipItem> nextTips(String word) {
        List<AutoCompleteTipItem> autoCompleteTipItemList = new ArrayList<>();
        AutoCompleteTipItem.AutoCompleteTipItemBuilder builder = new AutoCompleteTipItem.AutoCompleteTipItemBuilder("1", "2");
        autoCompleteTipItemList.add(builder.build());
        return autoCompleteTipItemList;
    }
}
