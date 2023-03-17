package com.mishcma.taskmanagmentsystem;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // create users
        User firstUser = new User(1L, "first", "password", "first@gmail.com");
        User secondUser = new User(2L, "second", "password", "second@gmail.com");

        // save user
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        
        // create tasks
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        Task firstTask = new Task(1L, "first_title", "first_desc", (short) 1, "To do", LocalDateTime.parse("2023-12-03T00:00:00+03:00", formatter), null);
        Task secondTask = new Task(2L, "second_title", "second_desc", (short) 2, "To do", LocalDateTime.parse("2023-12-03T00:00:00+03:00", formatter), null);

        // save task
        taskRepository.save(firstTask);
        taskRepository.save(secondTask);
    }
    
}
