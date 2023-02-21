package com.mishcma.taskmanagmentsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.mishcma.taskmanagmentsystem.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}