package com.example.dental.controllers;

import com.example.dental.entities.PW;
import com.example.dental.entities.StudentPW;
import com.example.dental.repositories.StudentPwRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/studentpw")
public class StudentPwController {

    @Autowired
    private StudentPwRepository studentPWRepository;

    // Exemple d'endpoint pour récupérer tous les StudentPW
    @GetMapping
    public List<StudentPW> getAllStudentPWs() {
        return studentPWRepository.findAll();
    }
    @GetMapping("/pwDetails/{studentId}")
    public ResponseEntity<List<Object[]>> getPwDetailsByStudentId(@PathVariable Long studentId) {
        List<Object[]> pwDetails = studentPWRepository.findPwDetailsByStudentId(studentId);
        return new ResponseEntity<>(pwDetails, HttpStatus.OK);
    }

    // Exemple d'endpoint pour récupérer un StudentPW par son ID
    @GetMapping("/{id}")
    public StudentPW getStudentPWById(@PathVariable int id) {
        return studentPWRepository.findById(id).orElse(null);
    }

    // Exemple d'endpoint pour ajouter un nouveau StudentPW
    @PostMapping
    public StudentPW addStudentPW(@RequestBody StudentPW studentPW) {
        return studentPWRepository.save(studentPW);
    }

    // Exemple d'endpoint pour mettre à jour un StudentPW existant
    @PutMapping("/{id}")
    public StudentPW updateStudentPW(@PathVariable int id, @RequestBody StudentPW updatedStudentPW) {
        StudentPW existingStudentPW = studentPWRepository.findById(id).orElse(null);

        if (existingStudentPW != null) {
            // Mettez à jour les propriétés nécessaires
            existingStudentPW.setTime(updatedStudentPW.getTime());
            existingStudentPW.setDate(updatedStudentPW.getDate());
            // Mettez à jour d'autres propriétés au besoin

            return studentPWRepository.save(existingStudentPW);
        } else {
            return null; // Ou lancez une exception appropriée si l'entité n'est pas trouvée
        }
    }

    // Exemple d'endpoint pour supprimer un StudentPW par son ID
    @DeleteMapping("/{id}")
    public void deleteStudentPW(@PathVariable int id) {
        studentPWRepository.deleteById(id);
    }
}
