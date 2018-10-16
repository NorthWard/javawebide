package com.north.lat.autocomplete.service;

import com.north.lat.autocomplete.model.AutoCompleteTipItem;

import java.util.List;

public abstract class AbstractJavaParserSearchService implements SearchService<String, AutoCompleteTipItem> {
    @Override
    public List<AutoCompleteTipItem> search(String params) {
        return null;
    }

    public abstract List<String> searchByFileName(String fileName);
}
