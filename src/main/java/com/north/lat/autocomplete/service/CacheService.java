package com.north.lat.autocomplete.service;

import java.util.List;

public  interface CacheService<T> {
    List<T> getByName(String name);
    void  put(String name, T value);
}
