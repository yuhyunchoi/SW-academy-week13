package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Locale {
    KO, EN, JP;

    @JsonCreator
    public static Locale fromString(String str){
        for(Locale value : Locale.values()){
            if(value.name().equalsIgnoreCase(str)){
                return value;
            }
        }
        return Locale.KO;
    }


    @Override
    public String toString(){
        return name().toLowerCase();
    }
}
