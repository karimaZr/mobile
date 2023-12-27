package com.example.dental.repositories;

import com.example.dental.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GroupeRepository extends JpaRepository<Groupe,Integer> {
}
