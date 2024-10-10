package com.nhnacademy.daily.model;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum ProjectType {
    PUBLIC, PRIVATE;

    @JsonCreator
    public static ProjectType forString(String name) {
        for(ProjectType value : ProjectType.values()) {
            if(value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return ProjectType.PRIVATE;
    }

    @Override
    public String toString(){
        return name().toLowerCase();
    }
}
