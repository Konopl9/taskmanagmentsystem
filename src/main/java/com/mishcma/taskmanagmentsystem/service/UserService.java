package com.mishcma.taskmanagmentsystem.service;

import java.util.List;

import com.mishcma.taskmanagmentsystem.entity.User;

public interface UserService {
    
    User getUserById(Long id);

    List<User> getUsers();

    User createUser(User User);

    User updateUserEmail(User newUser);

    void deleteUser(Long id);

    User assignTaskToUser(Long userId , Long taskId);
}
