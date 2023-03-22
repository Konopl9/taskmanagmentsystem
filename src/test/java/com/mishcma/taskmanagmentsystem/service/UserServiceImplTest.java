package com.mishcma.taskmanagmentsystem.service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById() {
        User user = new User(1L, "username", "password", "email", new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        verify(userRepository, times(1)).findById(1L);
        assertEquals(user, result);
    }

    @Test
    void getUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "username1", "password1", "email1", new ArrayList<>()));
        userList.add(new User(2L, "username2", "password2", "email2", new ArrayList<>()));
        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Pageable pageable = PageRequest.of(1, 2);
        Page<User> result = userService.getUsers(pageable);

        verify(userRepository, times(1)).findAll(pageable);
        assertEquals(userPage, result);
    }

    @Test
    void createUser() {
        User user = new User(1L, "username", "password", "email", new ArrayList<>());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    @Test
    void updateUserEmail() {
        User oldUser = new User(1L, "username", "password", "email1", new ArrayList<>());
        User newUser = new User(1L, "username", "password", "email2", new ArrayList<>());
        User updatedUser = new User(1L, "username", "password", "email2", new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUserEmail(newUser);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(updatedUser, result);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test

    public void testAssignTaskToUserSuccess() {
        User user = new User(1L, "testuser", "password", "testuser@test.com", Collections.emptyList());
        Task task = new Task(1L, "Test task", "Test task description", (short) 1, "TODO", null, null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        User assignedUser = userService.assignTaskToUser(1L, 1L);

        assertNotNull(assignedUser);

        verify(userRepository, times(2)).findById(1L);
        verify(taskRepository, times(1)).findById(1L);
    }
}