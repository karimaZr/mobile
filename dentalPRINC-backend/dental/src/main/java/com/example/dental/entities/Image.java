package com.example.dental.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String url;
    private  float alpha1;
    private  float alpha2;
    private  float alpha3;
    private  float beta1;
    private  float beta2;
    private  float beta3;
    @ManyToOne
    private StudentPW studentPW;

}
