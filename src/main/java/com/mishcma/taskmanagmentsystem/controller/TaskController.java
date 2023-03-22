package com.mishcma.taskmanagmentsystem.controller;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RequestMapping("/task")
@RestController
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Task>> getTasks(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskService.getTasks(pageable);
        return new ResponseEntity<>(taskPage, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Task>> getTasksByUserIdAndStatus(@PathVariable Long userId, @RequestBody Map<String, String> requestBody, @RequestParam int page, @RequestParam int size) {
        String status = requestBody.get("status");
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasksForUserByIdAndStatus = taskService.getTaskByUserAndStatus(userId, status, pageable);
        return ResponseEntity.ok(tasksForUserByIdAndStatus);
    }

    @GetMapping("/user/{userId}/today")
    public ResponseEntity<Page<Task>> getTasksByUserIdAndToday(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskDorUserByUserIdAndToday = taskService.getUserTodayTask(userId, pageable);
        return ResponseEntity.ok(taskDorUserByUserIdAndToday);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.reassignTask(taskId, userId));
    }

    @PatchMapping("/status")
    public ResponseEntity<Task> updateTaskStatusById(@Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTaskStatus(task.getId(), task.getStatus());
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/priority")
    public ResponseEntity<Task> updateTaskPriorityById(@Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTaskPriority(task.getId(), task.getPriority());
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/maturity_date")
    public ResponseEntity<Task> updateTaskMaturityDateById(@Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTaskMaturityDate(task.getId(), task.getMaturityDate());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
