package com.nhnacademy.daily.model;

import java.time.LocalDate;

public class ProjectCreateCommand {
    String code;
    LocalDate createdAt ;
    ProjectType type;

    public ProjectCreateCommand(String code) {
        this.code = code;
        this.createdAt = LocalDate.now();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }
}
