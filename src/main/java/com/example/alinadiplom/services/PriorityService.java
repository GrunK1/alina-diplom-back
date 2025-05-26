package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.Priority;
import com.example.alinadiplom.repositories.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository repository;

    public Priority create(Priority priority) {
        priority.setPriorityId(null);
        return repository.save(priority);
    }

    public List<Priority> getAll() {
        return repository.findAll();
    }

    public Priority getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found: " + id));
    }

    public Priority update(Integer id, Priority newData) {
        Priority priority = getById(id);
        priority.setPriorityType(newData.getPriorityType());
        return repository.save(priority);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}