package com.nhnacademy.demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN, MEMBER;

    @JsonCreator
    public static Role fromString(String str){
        for(Role role : Role.values()){
            if(role.name().equalsIgnoreCase(str)){
                return role;
            }
        }
        return MEMBER;
    }

    @JsonValue
    public String toString(){
        return this.name().toLowerCase();
    }
}
