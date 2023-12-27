package com.example.dental.controllers;

import com.example.dental.entities.Admin;
import com.example.dental.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @GetMapping
    public List<Admin> findAdmins(){
        return adminRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findAdminById(@PathVariable int id){
        Admin admin=adminRepository.findById(id).orElse(null);
        if(admin==null){
            return new ResponseEntity<Object>("Admin with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(admin);

        }
    }
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin){
        admin.setId(0);
        return adminRepository.save(admin);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAdmin(@PathVariable int id,@RequestBody Admin newAdmin){
        Admin oldAdmin=adminRepository.findById(id).orElse(null);
        if(oldAdmin==null){
            return new ResponseEntity<Object>("admin with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            newAdmin.setId(id);
            return ResponseEntity.ok(adminRepository.save(newAdmin));

        }


    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<Object> deleteAdmin(@PathVariable int id){
        Admin admin=adminRepository.findById(id).orElse(null);
        if(admin==null){
            return new ResponseEntity<Object>("admin with id "+id+"not exist", HttpStatus.BAD_REQUEST);
        }else {
            adminRepository.delete(admin);
            return ResponseEntity.ok("admin has been deleted");

        }
    }

}
