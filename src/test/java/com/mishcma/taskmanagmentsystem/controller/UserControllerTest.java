package com.mishcma.taskmanagmentsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.entity.User;
import com.mishcma.taskmanagmentsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Mock
    UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void getUserById() throws Exception {
        // create a user
        Long userId = 1L;
        User user = new User(userId, "test", "test", "test@gmail.com");

        // when
        when(userService.getUserById(userId)).thenReturn(user);

        // create a request
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", userId);

        // perform
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));

    }

    @Test
    void getUsers() throws Exception {
        // create users and pageable
        Page<User> usersPage = new PageImpl<>(List.of(
                new User(1L, "test1", "test", "test@gmail.com", null),
                new User(2L, "test2", "test", "test@gmail.com", null),
                new User(3L, "test3", "test", "test@gmail.com", null),
                new User(4L, "test4", "test", "test@gmail.com", null),
                new User(5L, "test5", "test", "test@gmail.com", null)));
        // when
        when(userService.getUsers(any())).thenReturn(usersPage);

        // create a request
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all?size=10&page=0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void createUser() throws Exception {
        // create user
        Long userId = 1L;
        User user = new User(userId, "test", "test", "test@gmail.com");
        // when
        when(userService.createUser(any(User.class))).thenReturn(user);

        // create a request
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId));

    }

    @Test
    void updateUserEmailById() throws Exception {
        // create user
        Long userId = 1L;
        String updatedEmail = "testUpdated@gmail.com";
        User user = new User(userId, "test", "test", "test@gmail.com");
        User updatedUser = new User(userId, "test", "test", updatedEmail);
        // when
        when(userService.updateUserEmail(any(User.class))).thenReturn(updatedUser);

        // create a request
        mockMvc.perform(MockMvcRequestBuilders.put("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(updatedEmail));
    }

    @Test
    void deleteUserById() throws Exception {
        // create user
        Long userId = 1L;
        User user = new User(userId, "test", "test", "test@gmail.com");
        when(userService.getUserById(userId)).thenReturn(user);

        // send delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", userId))
                .andExpect(status().isNoContent());

        // verify user has been deleted
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void assignTaskToUser() throws Exception {
        // create user
        User user = new User(1L, "test", "test", "test@gmail.com");

        // create task
        Task task = new Task(1L, "TaskTest", "testDesc", (short) 1, "To do", LocalDate.now(), user);

        // mock userService.assignTaskToUser() method
        when(userService.assignTaskToUser(user.getId(), task.getId())).thenReturn(user);
        user.setTasks(List.of(task));

        // perform PUT request with task ID and user ID
        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + user.getId() + "/task/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.tasks[0].title").value(task.getTitle()));
    }
}