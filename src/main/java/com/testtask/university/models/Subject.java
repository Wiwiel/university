package com.testtask.university.models;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private String name;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    public Subject(){}

    public Subject(String name, Teacher teacher){
        this.name = name;
        this.teacher = teacher;
    }

    public void change(String name, Teacher teacher){
        this.name = name;
        this.teacher = teacher;
    }

    public boolean teacherIsEmpty() {
        if (teacher == null)
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return teacher.getName();
    }
}
