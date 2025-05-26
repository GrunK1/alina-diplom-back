package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.Priority;
import com.example.alinadiplom.services.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/priorities")
public class PriorityController {

    @Autowired
    private PriorityService service;

    @PostMapping
    public ResponseEntity<Priority> create(@RequestBody Priority priority) {
        return new ResponseEntity<>(service.create(priority), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Priority>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Priority> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Priority> update(@PathVariable Integer id, @RequestBody Priority priority) {
        return ResponseEntity.ok(service.update(id, priority));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}