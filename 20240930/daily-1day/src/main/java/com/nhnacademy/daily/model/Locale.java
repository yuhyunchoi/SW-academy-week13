package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Locale {
    KO, EN, JP;

    @JsonValue
    public String toString(){
        return name().toLowerCase();
    }
}
