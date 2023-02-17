package com.mishcma.taskmanagmentsystem.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.mishcma.taskmanagmentsystem.entity.User;

public interface UserRepository extends CrudRepository<User, UUID> {

}