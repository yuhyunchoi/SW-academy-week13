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
}
