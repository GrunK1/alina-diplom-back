package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.Status;
import com.example.alinadiplom.model.Task;
import com.example.alinadiplom.model.TaskStatus;
import com.example.alinadiplom.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;
    private StatusService statusService;
    private TaskStatusService taskStatusService;

    @Transactional
    public Task create(Task task) {
        task.setTaskNumber(null);
        task = repository.save(task);

        /// добавление записи в смежную со статусами таблицу
        // получение статуса "запланировано". если его нет - добавляем
        Status taskStatus;
        try {
            taskStatus = statusService.getAll()
                    .stream().filter(x-> Objects.equals(x.getStatusType(), "Запланировано"))
                    .findFirst().orElseThrow();
        } catch (Exception e){
            System.out.println("Status \"Запланировано\" not found. Force adding it to DB.");
            Status planned = new Status();
            planned.setStatusType("Запланировано");
            taskStatus = statusService.create(planned);
        }
        // добавление записи в смежную таблицу
        TaskStatus taskStatusEntry = new TaskStatus();
        Date date = new Date();
        taskStatusEntry.setDateOfStatusSet(date);
        taskStatusEntry.setTimeOfStatusSet(new Time(date.getTime()));
        taskStatusEntry.setTaskNumber(task);
        taskStatusEntry.setStatusId(taskStatus);
        taskStatusService.create(taskStatusEntry);

        // возвращаем таску раз добавили
        return task;
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