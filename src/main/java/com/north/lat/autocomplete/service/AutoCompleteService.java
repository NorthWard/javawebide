package com.north.lat.autocomplete.service;

import com.north.lat.autocomplete.model.AutoCompleteCondition;
import com.north.lat.autocomplete.model.AutoCompleteTipItem;

import java.util.List;

public interface AutoCompleteService {

    List<AutoCompleteTipItem> nextTips(AutoCompleteCondition autoCompleteCondition);
}
