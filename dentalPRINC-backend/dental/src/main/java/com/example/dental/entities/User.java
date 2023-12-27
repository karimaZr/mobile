package com.example.dental.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String userName;
    protected String password;
    protected String firstName;
    protected String lastName;
    @Lob
    @Column(name = "photo",columnDefinition = "LONGBLOB")
    private byte[] image;



}
