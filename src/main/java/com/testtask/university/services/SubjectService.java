package com.testtask.university.services;

import com.testtask.university.dto.InputSubjectDto;
import com.testtask.university.models.Teacher;
import com.testtask.university.models.Subject;
import com.testtask.university.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    TeacherService teacherService;

    public SubjectService (SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    public Optional<Subject> getSubject(Integer id){
        return subjectRepository.findById(id);
    }

    public Optional<Subject> createSubject(InputSubjectDto subjectInfo){
        Optional<Subject> subject = subjectRepository.findByName(subjectInfo.getName());
        Optional<Teacher> teacher = teacherService.getTeacher(subjectInfo.getTeacherId());
        if(subject.isPresent())
            return Optional.empty();
        if(teacher.isEmpty())
            return Optional.of(new Subject(subjectInfo.getName(), null));
        Subject newSubject = new Subject(subjectInfo.getName(), teacher.get());
        subjectRepository.save(newSubject);
        return Optional.of(newSubject);
    }

    public Optional<Subject> saveSubject(Integer id, InputSubjectDto subjectInfo){
        Optional<Subject> subject = subjectRepository.findById(id);
        Optional<Teacher> teacher = teacherService.getTeacher(subjectInfo.getTeacherId());
        if (subject.isPresent() && teacher.isPresent()){
            subject.get().change(subjectInfo.getName(), teacher.get());
            subjectRepository.save(subject.get());
        } else subject.ifPresent(value -> value.change(subjectInfo.getName(), null));
        return subject;
    }

    public boolean deleteSubject(Integer id){
        if(subjectRepository.findById(id).isPresent()) {
            subjectRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }
}
