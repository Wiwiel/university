package com.testtask.university.services;

import com.testtask.university.models.Teacher;
import com.testtask.university.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService (TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }
    
    public Optional<Teacher> getTeacher(Integer id){
        return teacherRepository.findById(id);
    }

    public Teacher createTeacher(String teacherName){
        Teacher newTeacher = new Teacher(teacherName);
        teacherRepository.save(newTeacher);
        return newTeacher;
    }

    public Optional<Teacher> saveTeacher(Integer id, String newTeacherName){
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()){
            teacher.get().setName(newTeacherName);
            teacherRepository.save(teacher.get());
        }
        return teacher;
    }

    public boolean deleteTeacher(Integer id){
        if(teacherRepository.findById(id).isPresent()) {
            teacherRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }
}
