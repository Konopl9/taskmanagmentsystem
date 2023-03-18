package com.mishcma.taskmanagmentsystem.service;

import com.mishcma.taskmanagmentsystem.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);

    List<Task> getTasks();

    List<Task> getUserTodayTask(Long userId);

    Task createTask(Task task);

    Task updateTaskStatus(Long id, String status);

    Task updateTaskPriority(Long id, Short priority);

    Task updateTaskMaturityDate(Long id, LocalDate maturityDate);

    void deleteTask(Long id);

    List<Task> getTaskByUserAndStatus(Long userId, String status);

    Task reassignTask(Long taskId, Long userId);
}
