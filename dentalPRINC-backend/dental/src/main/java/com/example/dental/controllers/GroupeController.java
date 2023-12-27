package com.example.dental.controllers;

import com.example.dental.entities.Groupe;
import com.example.dental.repositories.GroupeRepository;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/groupe")
public class GroupeController {
    @Autowired
    GroupeRepository groupeRepository;
    @GetMapping
    public List<Groupe> findGroupes(){

        return groupeRepository.findAll();
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Object> findGroupeById(@PathVariable int id){
        Groupe groupe=groupeRepository.findById(id).orElse(null);
        if(groupe==null){
            return new ResponseEntity<Object>("Child with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(groupe);

        }
    }
    @PostMapping
    public Groupe createGroupe(@RequestBody Groupe groupe){
        groupe.setId(0);
        return groupeRepository.save(groupe);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGroupe(@PathVariable int id,@RequestBody Groupe newGroupe){
        Groupe oldGroupe=groupeRepository.findById(id).orElse(null);
        if(oldGroupe==null){
            return new ResponseEntity<Object>("groupe with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newGroupe.setId(id);
            return ResponseEntity.ok(groupeRepository.save(newGroupe));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteGroupe(@PathVariable int id){
        Groupe groupe=groupeRepository.findById(id).orElse(null);
        if(groupe==null){
            return new ResponseEntity<Object>("groupe with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            groupeRepository.delete(groupe);
            return ResponseEntity.ok("groupe has been deleted");

        }
    }

}