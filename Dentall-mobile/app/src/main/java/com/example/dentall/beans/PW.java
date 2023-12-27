package com.example.dentall.beans;

import java.io.Serializable;
import java.util.List;

public class PW implements Serializable {
    private int id;
    private String title;
    private String objectif;
    private String docs;
    private Tooth tooth;
    private List<Groupe> groupes;
    private List<StudentPW> studentPWS;

    // ... autres méthodes, getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public Tooth getTooth() {
        return tooth;
    }

    public void setTooth(Tooth tooth) {
        this.tooth = tooth;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public List<StudentPW> getStudentPWS() {
        return studentPWS;
    }

    public void setStudentPWS(List<StudentPW> studentPWS) {
        this.studentPWS = studentPWS;
    }
}
