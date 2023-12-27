package com.example.dental.controllers;

import com.example.dental.entities.PW;
import com.example.dental.repositories.PWRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/pw")
public class PwController {
    @Autowired
    PWRepository pwRepository;
    @GetMapping
    public List<PW> findPWs(){
        return pwRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findPwById(@PathVariable int id){
        PW pw=pwRepository.findById(id).orElse(null);
        if(pw==null){
            return new ResponseEntity<Object>("PW with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(pw);

        }
    }
    @PostMapping
    public PW createPW(@RequestBody PW pw){
        pw.setId(0);
        return pwRepository.save(pw);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePW(@PathVariable int id,@RequestBody PW newPW){
        PW oldPW=pwRepository.findById(id).orElse(null);
        if(oldPW==null){
            return new ResponseEntity<Object>("pw with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newPW.setId(id);
            return ResponseEntity.ok(pwRepository.save(newPW));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deletePw(@PathVariable int id){
        PW pw=pwRepository.findById(id).orElse(null);
        if(pw==null){
            return new ResponseEntity<Object>("pw with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            pwRepository.delete(pw);
            return ResponseEntity.ok("pw has been deleted");

        }
    }

}

