package com.testtask.university.services;

import com.testtask.university.dto.InputLectureDto;
import com.testtask.university.models.Lecture;
import com.testtask.university.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    GroupService groupService;
    @Autowired
    RoomService roomService;
    @Autowired
    SubjectService subjectService;

    public LectureService (LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    public Optional<Lecture> getLecture(Integer id){
        return lectureRepository.findById(id);
    }

    public Lecture createLecture(InputLectureDto lectureInfo){
        Lecture newLecture = new Lecture();
        lectureChange(newLecture, lectureInfo);
        return newLecture;
    }

    public Optional<Lecture> saveLecture(Integer id, InputLectureDto lectureInfo){
        Optional<Lecture> lecture = lectureRepository.findById(id);
        lecture.ifPresent(value -> lectureChange(value, lectureInfo));
        return lecture;
    }

    public boolean deleteLecture(Integer id){
        if(lectureRepository.findById(id).isPresent()) {
            lectureRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public List<Lecture> getLectures(){
        return lectureRepository.findAll();
    }

    private void lectureChange(Lecture lecture, InputLectureDto lectureInfo){
        groupService.getGroup(lectureInfo.getGroupId()).ifPresentOrElse(lecture::setGroup, () -> lecture.setGroup(null) );
        roomService.getRoom(lectureInfo.getRoomId()).ifPresentOrElse(lecture::setRoom, () -> lecture.setGroup(null) );
        subjectService.getSubject(lectureInfo.getGroupId()).ifPresentOrElse(lecture::setSubject, () -> lecture.setGroup(null) );
        lecture.setDay(lectureInfo.getDay());
        if(!lecture.hasNulls())
            lectureRepository.save(lecture);
    }
}
