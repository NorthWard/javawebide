package com.north.lat.autocomplete.service.impl;

import com.north.lat.autocomplete.model.CacheClassModel;
import com.north.lat.autocomplete.service.ClassCacheService;
import com.north.lat.es.EsManager;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author laihaohua
 */
@Service("esClassCacheService")
public class EsClassCacheServiceImpl extends ClassCacheService {

    private static String ES_TYPE = "ClassCache";
    private static String [] searchFiled = getFileds();

    private static  String [] getFileds(){
       Field [] fields =  CacheClassModel.class.getFields();
        String [] arr = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            arr[i] = fields[i].getName();
        }
        return arr;
    }
    @Override
    public List<CacheClassModel> getByName(String name) {
        List<Map<String, Object>> searchResult = EsManager.search(ES_TYPE, name,searchFiled);
        List<CacheClassModel> list = new ArrayList<>(searchResult.size());
        if(searchResult != null){

        }
        return list;
    }

    @Override
    public void put(String name, CacheClassModel value) {

    }
}
