package com.example.dentall.beans;

import java.io.Serializable;
import java.util.List;

public class Groupe implements Serializable {
    private Integer id;
    private String code;
    private String year;
    private List<Student> students;
    private List<PW> pwList;

    // ... autres méthodes, getters et setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<PW> getPwList() {
        return pwList;
    }

    public void setPwList(List<PW> pwList) {
        this.pwList = pwList;
    }
}