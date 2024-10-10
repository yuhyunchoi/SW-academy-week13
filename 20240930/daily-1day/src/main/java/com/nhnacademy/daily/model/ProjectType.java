package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectType {
    PUBLIC, PRIVATE;
    @JsonValue
    public String toString(){
        return name().toLowerCase();
    }
}
