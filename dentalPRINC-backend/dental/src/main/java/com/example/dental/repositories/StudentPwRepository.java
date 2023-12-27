package com.example.dental.repositories;

import com.example.dental.entities.PW;
import com.example.dental.entities.StudentPW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentPwRepository extends JpaRepository<StudentPW,Integer> {
    @Query("SELECT p.id,p.docs,p.objectif,p.title FROM StudentPW spw join PW p on p.id=spw.pw.id  WHERE spw.student.id =:studentId")
    List<Object[]> findPwDetailsByStudentId(@Param("studentId") Long studentId);
    List<StudentPW> findByStudentId(int studentId);

}
