package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import com.mishcma.taskmanagmentsystem.entity.User;

public interface UserService {
    
    public Optional<User> getUserById(Long id);

    public List<User> getUsers();

    public User createUser(User User);

    public User updateUserEmail(User newUser, Long id); 

    public void deleteUser(Long id);
}
