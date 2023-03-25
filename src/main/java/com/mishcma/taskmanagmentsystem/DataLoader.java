package com.mishcma.taskmanagmentsystem;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mishcma.taskmanagmentsystem.service.TaskService;
import com.mishcma.taskmanagmentsystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;
import com.mishcma.taskmanagmentsystem.repository.UserRepository;

import lombok.AllArgsConstructor;

@Profile("!test")
@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // create users
        User firstUser = new User(1L, "first", "password", "first@gmail.com");
        User secondUser = new User(2L, "second", "password", "second@gmail.com");

        // save user
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        
        // create tasks
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        Task firstTask = new Task(1L, "first_title", "first_desc", (short) 1, "To do", LocalDate.parse("2023-12-03", formatter), null);
        Task secondTask = new Task(2L, "second_title", "second_desc", (short) 2, "To do", LocalDate.parse("2023-12-03", formatter), null);

        // save task
        taskRepository.save(firstTask);
        taskRepository.save(secondTask);

        // assign task to user
        userService.assignTaskToUser(1L, 1L);
        userService.assignTaskToUser(2L, 2L);
    }

}
