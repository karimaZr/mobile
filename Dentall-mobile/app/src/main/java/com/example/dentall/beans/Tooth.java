package com.example.dentall.beans;

import java.io.Serializable;
import java.util.List;

public class Tooth implements Serializable {
    private int id;
    private String name;
    private List<PW> pwList;

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

    public List<PW> getPwList() {
        return pwList;
    }

    public void setPwList(List<PW> pwList) {
        this.pwList = pwList;
    }
}