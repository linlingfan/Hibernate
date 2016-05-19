package com.hibernate.llf.day5_02_HQL;

import com.hibernate.llf.day4_29o_to_m.Employee;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/4/29.
 */
public class Department {
    private int id;
    private String name;
    private Set<Employee> employeeSet=new HashSet<Employee>();
    public Department(){}

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Employee> employeeSet) {
        this.employeeSet = employeeSet;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
