package com.north.lat;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.base.Stopwatch;
import com.north.lat.classloder.AutoCompleteClassLoader;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * @author laihaohua
 */
@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootMain {

    public static void main(String[] args) throws Exception {
       AutoCompleteClassLoader completeClassLoader = AutoCompleteClassLoader.getInstance();
        //AutoCompleteClassLoader completeClassLoader = Thread.currentThread().getContextClassLoader();
        //completeClassLoader.getResource("java");
       // Reflections   reflections = new Reflections("org.");
//        Set<String> properties =
//                reflections.getResources(Pattern.compile(".*\\.java"));
        List<String> list = completeClassLoader.getAllFile();
        for(String file : list){
            getJarName(file);
            //CompilationUnit cu = JavaParser.parseResource(completeClassLoader, file, Charset.defaultCharset());
           // System.out.println(cu);
        }


      //  SpringApplication.run(SpringBootMain.class, args);
    }
    public static void getJarName(String jarFile) throws Exception {

        try{
            //通过jarFile和JarEntry得到所有的类
            JarFile jar = new JarFile(jarFile);
            //返回zip文件条目的枚举
            Enumeration<JarEntry> enumFiles = jar.entries();
            JarEntry entry;

            //测试此枚举是否包含更多的元素
            while(enumFiles.hasMoreElements()){
                entry = enumFiles.nextElement();
                if(entry.getName().indexOf("META-INF")< 0){
                    String classFullName = entry.getName();
                    if(classFullName.endsWith(".class")){
                        System.out.println(classFullName);
                    }
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}