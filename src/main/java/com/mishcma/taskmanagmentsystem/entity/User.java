package com.mishcma.taskmanagmentsystem.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Person {

    public User(Long id, String username, String password, String email) {
        super(id, username, password, email);
    }

    public User(Long id, String username, String password, String email, List<Task> tasks) {
        super(id, username, password, email);
        this.tasks = tasks;
    }

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
