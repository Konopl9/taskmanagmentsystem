package com.mishcma.taskmanagmentsystem.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<User>> getUsers(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> resultPage = userService.getUsers(pageable);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserEmailById(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUserEmail(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);   
    }

    @PutMapping("/{userId}/task/{taskId}")
    public ResponseEntity<User> assignTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
        return new ResponseEntity<>(userService.assignTaskToUser(userId, taskId), HttpStatus.OK);
    }
}

