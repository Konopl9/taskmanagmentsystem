package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return extractUser(userRepository.findById(id));
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
    public User updateUserEmail(User newUser, Long id) {
        Optional<User> unwrappedOldUser = userRepository.findById(id);
        
        User oldUser = extractUser(unwrappedOldUser);

        User updatedUser = new User(oldUser.getId(), oldUser.getUsername(), oldUser.getPassword(), newUser.getEmail(), oldUser.getTasks());

        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User extractUser(Optional<User> user) {
        if(user.isEmpty()) {
            throw new IllegalArgumentException("Incorrect User provided");
        }
        return user.get();
    }
    
}
