package com.testtask.university.dto;

import javax.validation.constraints.NotNull;

public class InputStudentDto {
    @NotNull(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Group id cannot be empty")
    private Integer groupId;

    public String getName() {
        return name;
    }

    public Integer getGroupId() {
        return groupId;
    }
}
