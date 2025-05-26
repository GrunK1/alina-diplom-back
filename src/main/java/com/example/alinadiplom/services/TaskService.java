package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.Task;
import com.example.alinadiplom.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Task create(Task task) {
        task.setTaskNumber(null);
        return repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

    public Task update(Long id, Task newData) {
        Task task = getById(id);
        task.setAddress(newData.getAddress());
        task.setComment(newData.getComment());
        task.setDateOfCreation(newData.getDateOfCreation());
        task.setMlNumber(newData.getMlNumber());
        task.setPdId(newData.getPdId());
        task.setPriorityId(newData.getPriorityId());
        task.setWtId(newData.getWtId());
        return repository.save(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}