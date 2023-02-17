package com.mishcma.taskmanagmentsystem.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.mishcma.taskmanagmentsystem.entity.Task;

public interface TaskRepository extends CrudRepository<Task, UUID> {

}