package com.mishcma.taskmanagmentsystem.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import jakarta.validation.constraints.Future;
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

    @Future
    @JsonProperty("maturity_date")
    @Column(name = "maturity_date")
    private LocalDateTime maturityDate;

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

    public Task(Long id, String title, String description, Short priority, String status, LocalDateTime maturityDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = LocalDateTime.now();
        this.maturityDate = maturityDate;
        this.user = user;
    }

    public Task(Long id, String title, String description, Short priority, String status, LocalDateTime creationDate, LocalDateTime maturityDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = creationDate;
        this.maturityDate = maturityDate;
        this.user = user;
    }
}
