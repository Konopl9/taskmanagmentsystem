package com.mishcma.taskmanagmentsystem.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {

    private UUID id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime creationDate;

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = LocalDateTime.now();
    }


}
