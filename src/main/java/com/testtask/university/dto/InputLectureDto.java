package com.testtask.university.dto;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

public class InputLectureDto {
    @NotNull(message = "Subject id cannot be empty")
    private Integer subjectId;

    @NotNull(message = "Room id cannot be empty")
    private Integer roomId;

    @NotNull(message = "Group id cannot be empty")
    private Integer groupId;

    @NotNull(message = "Day of week cannot be empty")
    private DayOfWeek day;

    public Integer getSubjectId() {
        return subjectId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public DayOfWeek getDay() {
        return day;
    }
}
