package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.exception.EntityNotFoundException;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return extractTask(task, id);
    }

    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task createTask(Task task, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new EntityNotFoundException(userId, User.class);
        }

        return taskRepository.save(new Task(task.getTitle(), task.getDescription(), task.getStatus(), user.get()));
    }

    public Task updateTaskStatus(Task newTask) {
        Task oldTask = getTaskById(newTask.getId());

        Task updatedTask = new Task(
                oldTask.getId(),
                oldTask.getTitle(),
                oldTask.getDescription(),
                newTask.getStatus(),
                oldTask.getCreationDate(),
                oldTask.getUser());

        return taskRepository.save(updatedTask);

    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private Task extractTask(Optional<Task> task, Long id) {
        if (task.isEmpty()) {
            throw new EntityNotFoundException(id, Task.class);
        }
        return task.get();
    }
}
