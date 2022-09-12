package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.InputNameDto;
import com.testtask.university.models.Teacher;
import com.testtask.university.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/teachers"}, produces = APPLICATION_JSON_VALUE)
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity getTeacher(@PathVariable Integer id){
        Optional<Teacher> teacher = teacherService.getTeacher(id);
        if(teacher.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(teacher.get()));
    }

    @PostMapping
    public ResponseEntity createTeacher(@Validated @RequestBody InputNameDto teacherName){
        Teacher teacher = teacherService.createTeacher(teacherName.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyTeacher(@PathVariable Integer id, @Validated @RequestBody InputNameDto teacherName){
        Optional<Teacher> teacher = teacherService.saveTeacher(id, teacherName.getName());
        if(teacher.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(teacher.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeacher(@PathVariable Integer id){
        if (teacherService.deleteTeacher(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllTeachers(){
        List<Teacher> teachers = teacherService.getTeachers();
        return ResponseEntity.ok(new Gson().toJson(teachers));
    }
}
