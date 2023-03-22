package com.mishcma.taskmanagmentsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.exception.EntityNotFoundException;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;

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

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getUserTodayTask(Long userId, Pageable pageable) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId, User.class));
        return taskRepository.findAllByUserIdAndMaturityDate(user.getId(), LocalDate.now(), pageable);
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
                oldTask.getStatus(),
                oldTask.getMaturityDate(), // pass the old maturity date here
                oldTask.getUser());

        updatedTask.setStatus(status); // set the new maturity date

        return taskRepository.save(updatedTask);
    }

    public Task updateTaskPriority(Long id, Short priority) {
        Task oldTask = getTaskById(id);

        Task updatedTask = new Task(
                oldTask.getId(),
                oldTask.getTitle(),
                oldTask.getDescription(),
                oldTask.getPriority(),
                oldTask.getStatus(),
                oldTask.getMaturityDate(), // pass the old maturity date here
                oldTask.getUser());

        updatedTask.setPriority(priority); // set the new maturity date

        return taskRepository.save(updatedTask);
    }

    public Task updateTaskMaturityDate(Long id, LocalDate maturityDate) {
        Task oldTask = getTaskById(id);

        Task updatedTask = new Task(
                oldTask.getId(),
                oldTask.getTitle(),
                oldTask.getDescription(),
                oldTask.getPriority(),
                oldTask.getStatus(),
                oldTask.getMaturityDate(), // pass the old maturity date here
                oldTask.getUser());

        updatedTask.setMaturityDate(maturityDate); // set the new maturity date

        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getTaskByUserAndStatus(Long userId, String status, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId, User.class));

        List<Task> tasks = user.getTasks().stream()
                .filter(task -> Objects.equals(task.getStatus(), status))
                .toList();

        int totalElements = tasks.size();

        int fromIndex = (int)pageable.getOffset();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);

        tasks = tasks.subList(fromIndex, toIndex);

        return new PageImpl<>(tasks, pageable, totalElements);
    }

    @Override
    public Task reassignTask(Long taskId, Long userId) {
        Task task = getTaskById(taskId);
        User newUser =  userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId, User.class));
        User oldUser =  userRepository.findById(task.getUser().getId()).orElseThrow(() -> new EntityNotFoundException(userId, User.class));
        task.setUser(newUser);
        return taskRepository.save(task);
    }

    public static Task extractTask(Optional<Task> task, Long id) {
        if (task.isEmpty()) {
            throw new EntityNotFoundException(id, Task.class);
        }
        return task.get();
    }
}
