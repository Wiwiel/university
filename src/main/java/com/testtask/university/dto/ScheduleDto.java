package com.testtask.university.dto;

import com.testtask.university.models.Lecture;

public class ScheduleDto {
    private String subject;
    private String teacher;
    private String room;

    public ScheduleDto(Lecture lecture){
        this.subject = lecture.getSubjectName();
        this.teacher = lecture.getTeacherName();
        this.room = lecture.getRoomName();
    }
}
