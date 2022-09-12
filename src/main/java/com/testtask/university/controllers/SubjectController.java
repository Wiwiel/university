package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.InputSubjectDto;
import com.testtask.university.models.Subject;
import com.testtask.university.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/subjects"}, produces = APPLICATION_JSON_VALUE)
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/{id}")
    public ResponseEntity getSubject(@PathVariable Integer id){
        Optional<Subject> subject = subjectService.getSubject(id);
        if(subject.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(subject.get()));
    }

    @PostMapping
    public ResponseEntity createSubject(@Validated @RequestBody InputSubjectDto subjectInfo){
        Optional<Subject> subject = subjectService.createSubject(subjectInfo);
        if(subject.isEmpty())
            return ResponseEntity.badRequest().body("Subject "+ subjectInfo.getName() + " already exists");
        if(subject.get().teacherIsEmpty()){
            return ResponseEntity.badRequest().body("Teacher id " + subjectInfo.getTeacherId() + " not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(subject.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifySubject(@PathVariable Integer id, @Validated @RequestBody InputSubjectDto subjectInfo){
        Optional<Subject> subject = subjectService.saveSubject(id, subjectInfo);
        if(subject.isEmpty())
            return ResponseEntity.notFound().build();
        if(subject.get().teacherIsEmpty()){
            return ResponseEntity.badRequest().body("Teacher id " + subjectInfo.getTeacherId() + " not found");
        }
        return ResponseEntity.ok(new Gson().toJson(subject.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubject(@PathVariable Integer id){
        if (subjectService.deleteSubject(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllSubjects(){
        List<Subject> subjects = subjectService.getSubjects();
        return ResponseEntity.ok(new Gson().toJson(subjects));
    }
}
