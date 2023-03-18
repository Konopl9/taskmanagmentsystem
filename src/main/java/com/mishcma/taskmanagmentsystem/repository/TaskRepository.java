package com.mishcma.taskmanagmentsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.mishcma.taskmanagmentsystem.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByUserIdAndMaturityDate(Long userId, LocalDate maturityDate);
}