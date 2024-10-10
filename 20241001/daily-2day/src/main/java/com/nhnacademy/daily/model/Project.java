package com.nhnacademy.daily.model;

import java.time.LocalDate;

public class Project {
    String code;
    LocalDate createdAt ;
    ProjectType type;

    public Project(String code) {
        this.code = code;
        this.createdAt = LocalDate.now();
    }

    public Project(String code, ProjectType type) {
        this.code = code;
        this.createdAt = LocalDate.now();
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ProjectType getType() {
        return type;
    }
}
