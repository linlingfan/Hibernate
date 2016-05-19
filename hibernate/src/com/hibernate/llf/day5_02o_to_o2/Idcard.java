package com.hibernate.llf.day5_02o_to_o2;

/**
 * Created by lenovo on 2016/5/3.
 */
public class Idcard {
    private int id;
    private String idnum;
    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Idcard{" +
                "id=" + id +
                ", idnum='" + idnum + '\'' +
                ", person=" + person +
                '}';
    }
}
