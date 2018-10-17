package com.north.lat.autocomplete.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 缓存类名
 * @author laihaohua
 */
@Data
public class CacheClassModel implements Serializable {
    private String className;
    private String classFullName;
    private String location;


}
