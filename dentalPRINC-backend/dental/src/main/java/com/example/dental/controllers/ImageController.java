package com.example.dental.controllers;

import com.example.dental.entities.Image;
import com.example.dental.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/image")
public class ImageController {

    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Get all images
    @GetMapping("/all")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable int id) {
        return imageRepository.findById(id)
                .map(image -> new ResponseEntity<>(image, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Save a new image
    @PostMapping("/save")
    public ResponseEntity<Image> saveImage(@RequestBody Image image) {
        Image savedImage = imageRepository.save(image);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }

    // Update an existing image
    @PutMapping("/update/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable int id, @RequestBody Image updatedImage) {
        return imageRepository.findById(id)
                .map(existingImage -> {
                    existingImage.setUrl(updatedImage.getUrl());
                    existingImage.setAlpha1(updatedImage.getAlpha1());
                    existingImage.setAlpha2(updatedImage.getAlpha2());
                    // Set other properties accordingly
                    Image updated = imageRepository.save(existingImage);
                    return new ResponseEntity<>(updated, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete an image
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable int id) {
        return imageRepository.findById(id)
                .map(image -> {
                    imageRepository.deleteById(id);
                    return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND));
    }
}
