package com.mishcma.taskmanagmentsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.mishcma.taskmanagmentsystem.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}