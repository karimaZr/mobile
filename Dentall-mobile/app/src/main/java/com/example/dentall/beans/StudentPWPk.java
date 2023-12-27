package com.example.dentall.beans;

import java.io.Serializable;

public class StudentPWPk implements Serializable {
    private int student;
    private int pw;

    // ... autres méthodes, getters et setters

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }
}