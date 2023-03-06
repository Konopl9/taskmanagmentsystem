package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

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

    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return extractUser(user, id);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserEmail(User newUser) {
        Optional<User> unwrappedOldUser = userRepository.findById(newUser.getId());
        
        User oldUser = extractUser(unwrappedOldUser, newUser.getId());

        User updatedUser = new User(oldUser.getId(), oldUser.getUsername(), oldUser.getPassword(), newUser.getEmail(), oldUser.getTasks());

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User extractUser(Optional<User> user, Long id) {
        if(user.isEmpty()) {
            throw new EntityNotFoundException(id, User.class);
        }
        return user.get();
    }

    @Override
    public User assignTaskToUser(Long userId, Long taskId) {
        Optional<User> wrapperdUser = userRepository.findById(userId);
        User user = extractUser(wrapperdUser, userId);
        
        Optional<Task> wrappedTask = taskRepository.findById(taskId);
        Task task = TaskServiceImpl.extractTask(wrappedTask, taskId);

        task.setUser(user);
        taskRepository.save(task);

        return userRepository.findById(user.getId()).get();
    }
    
}
