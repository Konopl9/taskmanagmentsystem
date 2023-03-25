package com.mishcma.taskmanagmentsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mishcma.taskmanagmentsystem.validation.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @FutureOrPresent
    @JsonProperty("maturity_date")
    @Column(name = "maturity_date")
    private LocalDate maturityDate;

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

    public Task(Long id, String title, String description, Short priority, String status, LocalDate maturityDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = LocalDateTime.now();
        this.maturityDate = maturityDate;
        this.user = user;
    }

    public Task(Long id, String title, String description, Short priority, String status, LocalDateTime creationDate, LocalDate maturityDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = creationDate;
        this.maturityDate = maturityDate;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(user, task.user);
    }
}
