package com.example.dental.entities;

public class PWDetailsDTO {
    private Long id;
    private String docs;
    private String objectif;
    private String title;

    public PWDetailsDTO() {
    }

    public PWDetailsDTO(Long id, String docs, String objectif, String title) {
        this.id = id;
        this.docs = docs;
        this.objectif = objectif;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
