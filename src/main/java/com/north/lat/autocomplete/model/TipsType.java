package com.north.lat.autocomplete.model;

public enum TipsType {
    // .提示
    DOT(1, "点"),
    // 普通字符提示
    CHAR(2,"字符"),
    // new 操作
    NEW(3, "new"),
    // import 操作
    IMPORT(4,"import" );

    private int value;
    private String name;
    private TipsType(int value, String name){
        this.name = name;
        this.value = value;
    }
    public TipsType getByValue(int value){
        for(TipsType tipsType : TipsType.values()){
               if(value == tipsType.value){
                      return tipsType;
               }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
