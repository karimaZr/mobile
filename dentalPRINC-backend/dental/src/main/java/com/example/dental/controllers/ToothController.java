package com.example.dental.controllers;

import com.example.dental.entities.Tooth;
import com.example.dental.repositories.ToothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/tooth")
public class ToothController {
    @Autowired
    ToothRepository toothRepository;
    @GetMapping
    public List<Tooth> findTooths(){
        return toothRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findToothById(@PathVariable int id){
        Tooth tooth=toothRepository.findById(id).orElse(null);
        if(tooth==null){
            return new ResponseEntity<Object>("Tooth with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(tooth);

        }
    }
    @PostMapping
    public Tooth createTooth(@RequestBody Tooth tooth){
        tooth.setId(0);
        return toothRepository.save(tooth);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTooth(@PathVariable int id,@RequestBody Tooth newTooth){
        Tooth oldTooth=toothRepository.findById(id).orElse(null);
        if(oldTooth==null){
            return new ResponseEntity<Object>("tooth with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newTooth.setId(id);
            return ResponseEntity.ok(toothRepository.save(newTooth));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteTooth(@PathVariable int id){
        Tooth tooth=toothRepository.findById(id).orElse(null);
        if(tooth==null){
            return new ResponseEntity<Object>("tooth with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            toothRepository.delete(tooth);
            return ResponseEntity.ok("tooth has been deleted");

        }
    }

}
