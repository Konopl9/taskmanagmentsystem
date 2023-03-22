package com.mishcma.taskmanagmentsystem.service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);

    Page<Task> getTasks(Pageable pageable);

    Page<Task> getUserTodayTask(Long userId, Pageable pageable);

    Task createTask(Task task);

    Task updateTaskStatus(Long id, String status);

    Task updateTaskPriority(Long id, Short priority);

    Task updateTaskMaturityDate(Long id, LocalDate maturityDate);

    void deleteTask(Long id);

    Page<Task> getTaskByUserAndStatus(Long userId, String status, Pageable pageable);

    Task reassignTask(Long taskId, Long userId);
}
