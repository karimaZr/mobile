package com.example.dental.repositories;

import com.example.dental.entities.PW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PWRepository  extends JpaRepository<PW,Integer> {

}
