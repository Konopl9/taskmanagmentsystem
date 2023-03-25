package com.mishcma.taskmanagmentsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.exception.EntityNotFoundException;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserEmail(User newUser) {
        User oldUser = getUserById(newUser.getId());
        User updatedUser = new User(oldUser.getId(), oldUser.getUsername(), oldUser.getPassword(), newUser.getEmail(), oldUser.getTasks());
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User assignTaskToUser(Long userId, Long taskId) {
        User user = getUserById(userId);
        Task task =  taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException(taskId, Task.class));
        task.setUser(user);
        taskRepository.save(task);
        return userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException(userId, User.class));
    }
    
}
