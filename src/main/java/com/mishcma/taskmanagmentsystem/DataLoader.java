package com.mishcma.taskmanagmentsystem;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // create users
        User firstUser = new User(1L, "first", "password", "first@gmail.com");
        User secondUser = new User(2L, "second", "password", "second@gmail.com");

        // save user
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        
    }
    
}
