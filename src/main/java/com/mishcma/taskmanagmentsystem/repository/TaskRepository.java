package com.mishcma.taskmanagmentsystem.repository;

import com.mishcma.taskmanagmentsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {

    Page<Task> findAllByUserIdAndMaturityDate(Long userId, LocalDate maturityDate, Pageable pageable);
}