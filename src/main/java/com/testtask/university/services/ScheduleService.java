package com.testtask.university.services;

import com.testtask.university.dto.ScheduleDto;
import com.testtask.university.models.Lecture;
import com.testtask.university.models.Student;
import com.testtask.university.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final LectureRepository lectureRepository;
    @Autowired
    StudentService studentService;

    public ScheduleService (LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    public List<ScheduleDto> getScheduler(@RequestParam Integer studentId, @RequestParam DayOfWeek day){
        Optional<Student> student = studentService.getStudent(studentId);
        Iterator<Lecture> schedule = null;
        List<ScheduleDto> scheduleOutput = new ArrayList<>();
        if (student.isPresent()) {
            schedule = lectureRepository.findDayGroupSchedule(student.get().getGroupId(), day).listIterator();
            while (schedule.hasNext())
                scheduleOutput.add(new ScheduleDto(schedule.next()));
        }
        return scheduleOutput;
    }
}
