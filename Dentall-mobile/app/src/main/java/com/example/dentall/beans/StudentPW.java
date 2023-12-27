package com.example.dentall.beans;

import java.io.Serializable;
import java.util.Date;

public class StudentPW implements Serializable {
    private StudentPWPk id;
    private Student student;
    private PW pw;
    private String time;
    private String imageFront;
    private String imageSide;
    private Date date;


    public StudentPWPk getId() {
        return id;
    }

    public void setId(StudentPWPk id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PW getPw() {
        return pw;
    }

    public void setPw(PW pw) {
        this.pw = pw;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageFront() {
        return imageFront;
    }

    public void setImageFront(String imageFront) {
        this.imageFront = imageFront;
    }

    public String getImageSide() {
        return imageSide;
    }

    public void setImageSide(String imageSide) {
        this.imageSide = imageSide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}