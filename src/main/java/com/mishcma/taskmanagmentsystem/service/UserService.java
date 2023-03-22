package com.mishcma.taskmanagmentsystem.service;


import com.mishcma.taskmanagmentsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    
    User getUserById(Long id);

    Page<User> getUsers(Pageable pageable);

    User createUser(User User);

    User updateUserEmail(User newUser);

    void deleteUser(Long id);

    User assignTaskToUser(Long userId , Long taskId);
}
