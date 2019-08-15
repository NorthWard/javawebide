package com.north.lat.autocomplete.service;

import java.util.List;

public  interface CacheService<T> {
    List<T> getByKeyWord(String keyWord);
    void  put(String keyWord, T value);
}
