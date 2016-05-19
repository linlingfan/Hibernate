package com.hibernate.llf.day4_30m_to_m;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/4/30.
 */
public class Teacher {
    private int id;
    private String name;
    private Set<Student> studentSet=new HashSet<Student>();

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

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
