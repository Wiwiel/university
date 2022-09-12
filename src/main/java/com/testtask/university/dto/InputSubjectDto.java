package com.testtask.university.dto;

import javax.validation.constraints.NotNull;

public class InputSubjectDto {
    @NotNull(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Teacher id cannot be empty")
    private Integer teacherId;

    public String getName() {
        return name;
    }

    public Integer getTeacherId() {
        return teacherId;
    }
}
