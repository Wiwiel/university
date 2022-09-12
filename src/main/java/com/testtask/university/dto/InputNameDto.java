package com.testtask.university.dto;

import javax.validation.constraints.NotNull;

public class InputNameDto {
    @NotNull(message = "Name cannot be blank")
    private String name;

    public String getName() {
        return name;
    }
}
