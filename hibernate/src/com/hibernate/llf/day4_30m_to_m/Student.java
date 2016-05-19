package com.hibernate.llf.day4_30m_to_m;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/4/30.
 */
public class Student {
    private int id;
    private String name;
    private Set<Teacher>teacherSet=new HashSet<Teacher>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
