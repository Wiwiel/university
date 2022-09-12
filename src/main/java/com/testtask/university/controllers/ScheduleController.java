package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.ScheduleDto;
import com.testtask.university.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/schedule"}, produces = APPLICATION_JSON_VALUE)
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity getSchedule(@RequestParam Integer studentId, @RequestParam DayOfWeek day){
        List<ScheduleDto> schedule = scheduleService.getScheduler(studentId, day);
        return ResponseEntity.ok(new Gson().toJson(schedule));
    }
}
