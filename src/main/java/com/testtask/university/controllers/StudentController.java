package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.InputStudentDto;
import com.testtask.university.models.Student;
import com.testtask.university.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/students"}, produces = APPLICATION_JSON_VALUE)
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity getStudent(@PathVariable Integer id){
        Optional<Student> student = studentService.getStudent(id);
        if(student.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(student.get()));
    }

    @PostMapping
    public ResponseEntity createStudent(@Validated @RequestBody InputStudentDto studentInfo){
        Optional<Student> student = studentService.createStudent(studentInfo);
        if(student.isEmpty())
            return ResponseEntity.badRequest().body("Group id" + studentInfo.getGroupId() + " not found");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(student.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyStudent(@PathVariable Integer id, @Validated @RequestBody InputStudentDto studentInfo){
        Optional<Student> student = studentService.saveStudent(id, studentInfo);
        if(student.isEmpty())
            return ResponseEntity.notFound().build();
        if(student.get().groupIsEmpty()){
            return ResponseEntity.badRequest().body("Group id " + studentInfo.getGroupId() + " not found");
        }
        return ResponseEntity.ok(new Gson().toJson(student.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Integer id){
        if (studentService.deleteStudent(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllStudents(){
        List<Student> students = studentService.getStudents();
        return ResponseEntity.ok(new Gson().toJson(students));
    }
}
