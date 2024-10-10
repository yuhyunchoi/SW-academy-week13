package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberCreateCommand {
    private String id;
    private String name;
    @JsonProperty("class")
    private String clazz;
    private Locale locale;

    public MemberCreateCommand() {

    }

    public MemberCreateCommand(String id, String name, String clazz, Locale locale) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
