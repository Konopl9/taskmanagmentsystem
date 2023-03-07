package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.exception.EntityNotFoundException;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return extractTask(task, id);
    }

    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id, String status) {
        Task oldTask = getTaskById(id);

        Task updatedTask = new Task(
                oldTask.getId(),
                oldTask.getTitle(),
                oldTask.getDescription(),
                oldTask.getPriority(),
                status,
                oldTask.getCreationDate(),
                oldTask.getUser());

        return taskRepository.save(updatedTask);
    }

    public Task updateTaskPriority(Long id, Short priority) {
        Task oldTask = getTaskById(id);

        Task updatedTask = new Task(
                oldTask.getId(),
                oldTask.getTitle(),
                oldTask.getDescription(),
                priority,
                oldTask.getStatus(),
                oldTask.getCreationDate(),
                oldTask.getUser());

        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public static Task extractTask(Optional<Task> task, Long id) {
        if (task.isEmpty()) {
            throw new EntityNotFoundException(id, Task.class);
        }
        return task.get();
    }
}
