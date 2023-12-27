package com.example.dental.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@NoArgsConstructor
public class StudentPW {
    @EmbeddedId
    private StudentPWPk id;
    @JoinColumn(name = "student", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Student student;
    @JoinColumn(name = "pw", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private PW pw;

    private String time;
    @Temporal(TemporalType.DATE)
    private Date date;
@OneToMany(mappedBy = "studentPW")
private List<Image> images;


}
