package com.mishcma.taskmanagmentsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId, @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        List<Task> tasksForUserByIdAndStatus = taskService.getTaskByUserAndStatus(userId, status);
        return ResponseEntity.ok(tasksForUserByIdAndStatus);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
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
