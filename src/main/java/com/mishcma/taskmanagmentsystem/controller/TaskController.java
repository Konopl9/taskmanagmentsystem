package com.mishcma.taskmanagmentsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.service.TaskService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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
    public ResponseEntity<List<Task>> getTasks() {
        return new ResponseEntity<>(taskService.getTasks(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserIdAndStatus(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        List<Task> tasksForUserByIdAndStatus = taskService.getTaskByUserAndStatus(userId, status);
        return ResponseEntity.ok(tasksForUserByIdAndStatus);
    }

    @GetMapping("/user/{userId}/today")
    public ResponseEntity<List<Task>> getTasksByUserIdAndToday(@PathVariable Long userId) {
        List<Task> taskDorUserByUserIdAndToday = taskService.getUserTodayTask(userId);
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
