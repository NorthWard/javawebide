package com.north.lat.classloder;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laihaohua
 */
public class AutoCompleteClassLoader  extends URLClassLoader {
    private static volatile AutoCompleteClassLoader INSTANCE = null;
    private static List<URL>  list = new ArrayList<>(50);
    private AutoCompleteClassLoader(URL[] urls) {
        super(urls);
    }
    public static AutoCompleteClassLoader getInstance() throws MalformedURLException {
        if(INSTANCE == null){
             synchronized (AutoCompleteClassLoader.class){
                    if(INSTANCE == null){

                      String [] files = System.getProperty("sun.boot.class.path").split(";");
                      for(String file : files){
                          list.add(new URL("file:" + file));
                      }
                      files = System.getProperty("java.ext.dirs").split(";");
                        for(String file : files){
                            list.add(new URL("file:" + file));
                      }
                      files = System.getProperty("java.class.path").split(";");
                        for(String file : files){
                            list.add(new URL("file:" + file));
                      }
                        URL[] urls = new URL[list.size()];
                        INSTANCE = new AutoCompleteClassLoader(list.toArray(urls));
                    }
             }
        }
        return INSTANCE;
    }

    public List<String> getAllFile(){
        List<String> fileList = new ArrayList<>();
        for(URL url : list){
            fileList.add(url.getFile());
        }
        return  fileList;
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        final Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        try {
            Class<?> aClass = findClass(name);
            if (resolve) {
                resolveClass(aClass);
            }
            return aClass;
        } catch (Exception e) {
            return super.loadClass(name, resolve);
        }
    }
}
