package com.example.dental.repositories;

import com.example.dental.entities.PW;
import com.example.dental.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StudentRepository  extends JpaRepository<Student,Integer> {

}
