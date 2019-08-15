package com.north.lat;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laihaohua
 */
@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootMain {

    public static void main(String[] args) throws Exception {
       SpringApplication.run(SpringBootMain.class, args);
    }

}