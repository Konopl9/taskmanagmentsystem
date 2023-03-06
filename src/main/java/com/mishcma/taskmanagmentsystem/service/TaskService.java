package com.mishcma.taskmanagmentsystem.service;

import java.util.List;

import com.mishcma.taskmanagmentsystem.entity.Task;

public interface TaskService {
    public Task getTaskById(Long id);

    public List<Task> getTasks();

    public Task createTask(Task task);

    public Task updateTaskStatus(Task newTask); 

    public void deleteTask(Long id);

}
