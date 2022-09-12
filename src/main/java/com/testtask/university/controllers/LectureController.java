package com.testtask.university.controllers;

import com.google.gson.Gson;
import com.testtask.university.dto.InputLectureDto;
import com.testtask.university.models.Lecture;
import com.testtask.university.services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/lectures"}, produces = APPLICATION_JSON_VALUE)
public class LectureController {
    @Autowired
    LectureService lectureService;

    @GetMapping("/{id}")
    public ResponseEntity getLecture(@PathVariable Integer id){
        Optional<Lecture> lecture = lectureService.getLecture(id);
        if(lecture.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new Gson().toJson(lecture.get()));
    }

    @PostMapping
    public ResponseEntity createLecture(@Validated @RequestBody InputLectureDto lectureInfo){
        Lecture lecture = lectureService.createLecture(lectureInfo);
        if(lecture.hasNulls())
            return ResponseEntity.badRequest().body("Subject, room or group id not found");
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(lecture));
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyLecture(@PathVariable Integer id, @Validated @RequestBody InputLectureDto lectureInfo){
        Optional<Lecture> lecture = lectureService.saveLecture(id, lectureInfo);
        if(lecture.isEmpty())
            return ResponseEntity.notFound().build();
        if(lecture.get().hasNulls()){
            return ResponseEntity.badRequest().body("Subject, room or group id not found");
        }
        return ResponseEntity.ok(new Gson().toJson(lecture.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLecture(@PathVariable Integer id){
        if (lectureService.deleteLecture(id))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllLectures(){
        List<Lecture> lectures = lectureService.getLectures();
        return ResponseEntity.ok(new Gson().toJson(lectures));
    }
}
