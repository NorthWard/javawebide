package com.north.lat.autocomplete.service.impl;

import com.north.lat.autocomplete.model.CacheClassModel;
import com.north.lat.autocomplete.service.ClassCacheService;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laihaohua
 */
@Service("ehcacheClassCacheService")
public class EhcacheClassCacheServiceImpl extends ClassCacheService {
    private static final String CACHE_NAME = "CLASS_CACHE_SERVICE_CACHE";
    CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache(CACHE_NAME, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, CacheClassModel.class, ResourcePoolsBuilder.heap(10)))
            .build(true);
    Cache<String, CacheClassModel> CACHE = cacheManager.getCache(CACHE_NAME, String.class, CacheClassModel.class);

    @Override
    public List<CacheClassModel> getByName(String name) {
        return null;
    }

    @Override
    public void put(String name, CacheClassModel value) {

    }
}
