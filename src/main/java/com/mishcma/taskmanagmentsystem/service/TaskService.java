package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mishcma.taskmanagmentsystem.entity.Task;

@Service
public interface TaskService {
    public Optional<Task> getTaskById(Long id);

    public List<Task> getTasks();

    public Task createTask(Task task);

    public Task updateTaskStatus(Task newTask, Long id); 

    public void deleteTask(Long id);
}
