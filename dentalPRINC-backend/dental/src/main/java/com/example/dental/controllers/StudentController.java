package com.example.dental.controllers;

import com.example.dental.entities.PW;
import com.example.dental.entities.Student;
import com.example.dental.entities.StudentPW;
import com.example.dental.repositories.StudentPwRepository;
import com.example.dental.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentPwRepository studentPwRepository;
    @GetMapping("/{studentId}/pratiqueWorks")
    public Set<PW> getPratiqueWorksByStudent(@PathVariable int studentId) {
        List<StudentPW> studentPWs = studentPwRepository.findByStudentId(studentId);

        return studentPWs.stream()
                .map(StudentPW::getPw)
                .collect(Collectors.toSet());
    }
    @GetMapping
    public List<Student> findStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findStudentById(@PathVariable int id){
        Student student=studentRepository.findById(id).orElse(null);
        if(student==null){
            return new ResponseEntity<Object>("Student with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(student);

        }
    }
    @PostMapping
    public Student createStudent(@RequestBody Student student){
        student.setId(0);
        return studentRepository.save(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable int id,@RequestBody Student newStudent){
        Student oldStudent=studentRepository.findById(id).orElse(null);
        if(oldStudent==null){
            return new ResponseEntity<Object>("student with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newStudent.setId(id);
            return ResponseEntity.ok(studentRepository.save(newStudent));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteStudent(@PathVariable int id){
        Student student=studentRepository.findById(id).orElse(null);
        if(student==null){
            return new ResponseEntity<Object>("student with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            studentRepository.delete(student);
            return ResponseEntity.ok("student has been deleted");

        }
    }

}

