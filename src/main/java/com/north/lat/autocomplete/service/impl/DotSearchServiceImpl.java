package com.north.lat.autocomplete.service.impl;

import com.north.lat.autocomplete.model.AutoCompleteCondition;
import com.north.lat.autocomplete.model.AutoCompleteTipItem;
import com.north.lat.autocomplete.service.DefaultSearchService;

import java.util.List;

public class DotSearchServiceImpl extends DefaultSearchService {
    @Override
    public List<AutoCompleteTipItem> search(AutoCompleteCondition params) {
        String word = params.getWord();
        return null;
    }
}
