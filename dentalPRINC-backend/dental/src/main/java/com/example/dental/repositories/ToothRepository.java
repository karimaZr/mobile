package com.example.dental.repositories;

import com.example.dental.entities.Tooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToothRepository  extends JpaRepository<Tooth,Integer> {

}
