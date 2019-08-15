package com.north.lat.autocomplete.service.impl;

import com.north.lat.autocomplete.model.CacheClassModel;
import com.north.lat.autocomplete.service.ClassCacheService;
import com.north.lat.classloder.AutoCompleteClassLoader;
import com.north.lat.es.EsManager;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author laihaohua
 */
@Service("esClassCacheService")
public class EsClassCacheServiceImpl extends ClassCacheService {

    private static final  String ES_TYPE = "class_cache";
    private static final  String ES_CONFIG_TYPE = "es_config_type";
    private static final  String ES_CONFIG_TYPE_INIT_FLAG_ID = "es_class_cache_service_has_init";
    private static final  String [] searchFiled = getFields();

    private static  String [] getFields(){
       Field [] fields =  CacheClassModel.class.getFields();
        String [] arr = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            arr[i] = fields[i].getName();
        }
        return arr;
    }
    @PostConstruct
    public void  init() throws MalformedURLException {
/*
        if(!EsManager.exists(ES_CONFIG_TYPE, ES_CONFIG_TYPE_INIT_FLAG_ID)){
*/
//            EsManager.put(ES_CONFIG_TYPE,ES_CONFIG_TYPE_INIT_FLAG_ID, new Flag());
            AutoCompleteClassLoader completeClassLoader = AutoCompleteClassLoader.getInstance();
            List<String> list = completeClassLoader.getAllFile();
            for(String file : list){
                try{
                    //通过jarFile和JarEntry得到所有的类
                    JarFile jar = new JarFile(file);
                    //返回zip文件条目的枚举
                    Enumeration<JarEntry> enumFiles = jar.entries();
                    JarEntry entry;

                    //测试此枚举是否包含更多的元素
                    while(enumFiles.hasMoreElements()){
                        entry = enumFiles.nextElement();
                        if(entry.getName().indexOf("META-INF")< 0){
                            CacheClassModel cacheClassModel = new CacheClassModel();
                            String classFullName = entry.getName();
                            if(classFullName.endsWith(".class") && !classFullName.contains("$")){
                                System.out.println(classFullName);
                                cacheClassModel.setClassFullName(classFullName.replaceAll("/",".").replaceAll(".class", ""));
                                classFullName = cacheClassModel.getClassFullName();
                                String [] arr = classFullName.split("\\.");
                                cacheClassModel.setClassName(arr[arr.length - 1]);
                                cacheClassModel.setLocation(file);
                                try {
                                    completeClassLoader.loadClass(classFullName);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                //  put("", cacheClassModel);
                            }
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
/*        }*/

    }
    @Override
    public List<CacheClassModel> getByKeyWord(String keyWord) {
        List<Map<String, Object>> searchResultList = EsManager.search(ES_TYPE, keyWord,searchFiled);
        List<CacheClassModel> list = new ArrayList<>(searchResultList.size());
        if(searchResultList != null){
             for(Map<String, Object> map : searchResultList){
                 CacheClassModel cacheClassModel = new CacheClassModel();
                 BeanMap beanMap = BeanMap.create(cacheClassModel);
                 beanMap.putAll(map);
                 list.add(cacheClassModel);
             }
        }
        return list;
    }

    @Override
    public void put(String keyWord, CacheClassModel value) {
        EsManager.index(ES_TYPE, value);
    }
    static class Flag implements Serializable {
        private Date date;
        public  Flag(){
            this.date = new Date();
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
