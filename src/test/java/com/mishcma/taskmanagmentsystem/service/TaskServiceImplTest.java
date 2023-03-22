package com.mishcma.taskmanagmentsystem.service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.exception.EntityNotFoundException;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task(1L, "Test Task", "This is a test task", (short) 1, "OPEN", null, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        verify(taskRepository, times(1)).findById(1L);
        Assertions.assertEquals(task, result);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    public void testGetTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Test Task 1", "This is test task 1", (short) 1, "OPEN", null, null));
        tasks.add(new Task(2L, "Test Task 2", "This is test task 2", (short) 2, "CLOSED", null, null));
        Page<Task> userPage = new PageImpl<>(tasks);

        when(taskRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Pageable pageable = PageRequest.of(1, 2);
        Page<Task> result = taskService.getTasks(pageable);

        verify(taskRepository, times(1)).findAll(pageable);
        Assertions.assertEquals(userPage, result);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(1L, "Test Task", "This is a test task", (short) 1, "OPEN", null, null);
        when(taskRepository.save(any())).thenReturn(task);

        Task result = taskService.createTask(task);

        verify(taskRepository, times(1)).save(task);
        Assertions.assertEquals(task, result);
    }

    @Test
    public void testUpdateTaskStatus() {
        Task oldTask = new Task(1L, "Test Task", "This is a test task", (short) 1, "To do", null, null);
        Task updatedTask = new Task(1L, "Test Task", "This is a test task", (short) 1, "In progress", null, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any())).thenReturn(updatedTask);

        Task result = taskService.updateTaskStatus(1L, "In progress");

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
        Assertions.assertEquals(updatedTask, result);
    }

    @Test
    public void testUpdateTaskPriority() {
        Task oldTask = new Task(1L, "Test Task", "This is a test task", (short) 1, "To do", null, null);
        Task updatedTask = new Task(1L, "Test Task", "This is a test task", (short) 1, "In progress", null, null);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any())).thenReturn(updatedTask);

        Task result = taskService.updateTaskPriority(1L, (short) 2);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
        Assertions.assertEquals(updatedTask, result);
    }

    @Test
    public void testDeleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}