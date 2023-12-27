package com.example.dental.controllers;

import com.example.dental.entities.Professor;
import com.example.dental.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    ProfessorRepository professorRepository;
    @GetMapping
    public List<Professor> findProfessors(){
        return professorRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findProfessorById(@PathVariable int id){
        Professor professor=professorRepository.findById(id).orElse(null);
        if(professor==null){
            return new ResponseEntity<Object>("Professor with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(professor);

        }
    }
    @PostMapping
    public Professor createProfessor(@RequestBody Professor professor){
        professor.setId(0);
        return professorRepository.save(professor);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProfessor(@PathVariable int id,@RequestBody Professor newProfessor){
        Professor oldProfessor=professorRepository.findById(id).orElse(null);
        if(oldProfessor==null){
            return new ResponseEntity<Object>("professor with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newProfessor.setId(id);
            return ResponseEntity.ok(professorRepository.save(newProfessor));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteProfessor(@PathVariable int id){
        Professor professor=professorRepository.findById(id).orElse(null);
        if(professor==null){
            return new ResponseEntity<Object>("professor with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            professorRepository.delete(professor);
            return ResponseEntity.ok("professor has been deleted");

        }
    }

}
