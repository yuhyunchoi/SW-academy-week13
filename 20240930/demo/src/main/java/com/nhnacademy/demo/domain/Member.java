package com.nhnacademy.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member {
    private String id;
    private String password;
    private String name;
    private Integer age;
    @JsonProperty("class")
    private ClassType clazz;
    private Role role;

    public Member() {
    }

    public Member(String id, String name, Integer age, ClassType clazz, Role role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.clazz = clazz;
        this.role = role;
    }

    public Member(String id, String name, Integer age, ClassType clazz, Role role, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.clazz = clazz;
        this.role = role;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public ClassType getClazz() {
        return clazz;
    }

    public Role getRole() {
        return role;
    }
}
