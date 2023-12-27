package com.example.dental.repositories;

import com.example.dental.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfessorRepository  extends JpaRepository<Professor,Integer> {
}
