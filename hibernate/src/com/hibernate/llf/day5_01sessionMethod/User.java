package com.hibernate.llf.day5_01sessionMethod;

/**
 * Created by lenovo on 2016/4/21.
 */
public class User {
    private int id;
    private String name;

    private byte[] data=new byte[1024*1024*250];



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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
