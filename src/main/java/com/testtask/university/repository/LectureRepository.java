package com.testtask.university.repository;

import com.testtask.university.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    @Query("SELECT l from Lecture l WHERE l.day=:day and l.group.id=:groupId")
    List<Lecture> findDayGroupSchedule(Integer groupId, DayOfWeek day);
}
