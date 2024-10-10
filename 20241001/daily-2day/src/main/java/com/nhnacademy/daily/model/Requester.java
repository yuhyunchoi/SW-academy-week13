package com.nhnacademy.daily.model;


import java.util.Locale;

public class Requester {
    private String ip;
    private java.util.Locale lang;
    public Requester(String ip, java.util.Locale lang){
        this.ip = ip;
        this.lang = lang;
    }

    public String getIp(){
        return ip;
    }

    public Locale getLang(){
        return lang;
    }
}
