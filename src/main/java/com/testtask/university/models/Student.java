package com.testtask.university.models;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    public Student(){}

    public Student(String name, Group group){
        this.name = name;
        this.group = group;
    }

    public void change(String name, Group group){
        this.name = name;
        this.group = group;
    }

    public boolean groupIsEmpty() {
        if (group == null)
            return true;
        return false;
    }

    public Integer getGroupId(){
        return group.getId();
    }
}
