package com.mishcma.taskmanagmentsystem.repository;

import com.mishcma.taskmanagmentsystem.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByUserIdAndMaturityDate(Long userId, LocalDate maturityDate);
}