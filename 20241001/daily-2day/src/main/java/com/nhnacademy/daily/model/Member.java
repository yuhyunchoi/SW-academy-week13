package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member {
    private String id;
    private String password;
    private String name;
    //TODO 주석을 제거하고 해결해보세요.
    @JsonProperty("class")
    private String clazz;
    private Locale locale;
    private Role role;

    public Member(String id,String password, String name, String clazz, Locale locale, Role role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.clazz = clazz;
        this.locale = locale;
        this.role = role;
    }

    public String getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public String getClazz(){
        return clazz;
    }

    public Locale getLocale(){
        return locale;
    }

    public Role getRole(){
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
