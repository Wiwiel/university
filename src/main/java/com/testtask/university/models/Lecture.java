package com.testtask.university.models;
import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    private DayOfWeek day;

    public Lecture(){}

    public Lecture(Subject subject, Room room, Group group, DayOfWeek day){
        this.subject = subject;
        this.room = room;
        this.group = group;
        this.day = day;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public boolean hasNulls(){
        return (subject==null) || (room==null) || (group==null);
    }

    public String getSubjectName(){
        return subject.getName();
    }

    public String getTeacherName(){
        return subject.getTeacherName();
    }

    public String getRoomName(){
        return room.getName();
    }
}
