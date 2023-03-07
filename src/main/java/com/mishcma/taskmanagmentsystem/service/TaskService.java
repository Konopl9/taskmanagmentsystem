package com.mishcma.taskmanagmentsystem.service;

import java.util.List;

import com.mishcma.taskmanagmentsystem.entity.Task;

public interface TaskService {
    public Task getTaskById(Long id);

    public List<Task> getTasks();

    public Task createTask(Task task);

    public Task updateTaskStatus(Long id, String status); 

    public Task updateTaskPriority(Long id, Short priority);

    public void deleteTask(Long id);

}
