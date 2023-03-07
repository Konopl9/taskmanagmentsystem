package com.mishcma.taskmanagmentsystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mishcma.taskmanagmentsystem.validation.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Min(value = 1, message = "Priority can't be lower than 1")
    @Max(value = 5, message = "Priority can't be higer than 5")
    @Column(name = "priority")
    private Short priority;

    @NotNull
    @TaskStatus
    @Column(name = "status")
    private String status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @PrePersist
    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
