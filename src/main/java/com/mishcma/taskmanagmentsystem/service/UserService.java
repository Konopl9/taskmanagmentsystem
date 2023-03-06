package com.mishcma.taskmanagmentsystem.service;

import java.util.List;

import com.mishcma.taskmanagmentsystem.entity.User;

public interface UserService {
    
    public User getUserById(Long id);

    public List<User> getUsers();

    public User createUser(User User);

    public User updateUserEmail(User newUser); 

    public void deleteUser(Long id);

    public User assignTaskToUser(Long userId , Long taskId);
}
