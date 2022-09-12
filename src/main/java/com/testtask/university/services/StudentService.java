package com.testtask.university.services;

import com.testtask.university.dto.InputStudentDto;
import com.testtask.university.models.Group;
import com.testtask.university.models.Student;
import com.testtask.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    GroupService groupService;

    public StudentService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudent(Integer id){
        return studentRepository.findById(id);
    }

    public Optional<Student> createStudent(InputStudentDto studentInfo){
        Optional<Group> group = groupService.getGroup(studentInfo.getGroupId());
        if(group.isEmpty())
            return Optional.empty();
        Student newStudent = new Student(studentInfo.getName(), group.get());
        studentRepository.save(newStudent);
        return Optional.of(newStudent);
    }

    public Optional<Student> saveStudent(Integer id, InputStudentDto studentInfo){
        Optional<Student> student = studentRepository.findById(id);
        Optional<Group> group = groupService.getGroup(studentInfo.getGroupId());
        if (student.isPresent() && group.isPresent()){
            student.get().change(studentInfo.getName(), group.get());
            studentRepository.save(student.get());
        } else student.ifPresent(value -> value.change(studentInfo.getName(), null));
        return student;
    }

    public boolean deleteStudent(Integer id){
        if(studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
}
