package com.mishcma.taskmanagmentsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mishcma.taskmanagmentsystem.PageDeserializer;
import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.service.TaskService;
import com.mishcma.taskmanagmentsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority((short) 1);
        task.setStatus("To do");
        task.setMaturityDate(LocalDate.now());
    }

    @Test
    @DisplayName("GET /task/{id} returns existing task")
    public void testGetTaskByIdReturnsExistingTask() throws Exception {
        Task savedTask = taskService.createTask(task);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Task returnedTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertEquals(savedTask, returnedTask);
    }

    @Test
    @DisplayName("GET /task/{id} returns 404 for non-existent task")
    public void testGetTaskByIdReturns404ForNonExistentTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/task/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /task/all returns all tasks")
    public void testGetTasksReturnsAllTasks() throws Exception {
        Task savedTask = taskService.createTask(task);

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Page.class, new PageDeserializer<>());
        objectMapper.registerModule(module);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Page<Task> taskPage = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Page<Task>>() {
        });
        assertTrue(taskPage.getContent().contains(savedTask));
    }

    @Test
    @DisplayName("POST /task creates a new task")
    public void testCreateTask() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Test Task");
        newTask.setDescription("New Test Description");
        newTask.setPriority((short) 2);
        newTask.setStatus("To do");
        newTask.setMaturityDate(LocalDate.now().plusDays(7));

        String requestBody = objectMapper.writeValueAsString(newTask);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Task createdTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertEquals(newTask.getTitle(), createdTask.getTitle());
        assertEquals(newTask.getDescription(), createdTask.getDescription());
        assertEquals(newTask.getPriority(), createdTask.getPriority());
        assertEquals(newTask.getStatus(), createdTask.getStatus());
        assertEquals(newTask.getMaturityDate(), createdTask.getMaturityDate());
    }

    @Test
    @DisplayName("PUT /task/{id}/status updates task status")
    void testUpdateTaskStatusById() throws Exception {
        // Create a task
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority((short) 1);
        task.setStatus("To do");
        task.setMaturityDate(LocalDate.now());
        Task savedTask = taskService.createTask(task);

        // Update the status of the task
        String newStatus = "In progress";
        task.setStatus(newStatus);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/task/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn();


        // Verify that the task has the updated status
        Task updatedTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertEquals(newStatus, updatedTask.getStatus());
    }

    @Test
    @DisplayName("PUT /task/{id}/priority updates priority of existing task")
    public void testUpdateTaskPriorityByIdUpdatesPriorityOfExistingTask() throws Exception {
        // Create a task
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority((short) 1);
        task.setStatus("To do");
        task.setMaturityDate(LocalDate.now());
        Task savedTask = taskService.createTask(task);

        // Update the priority of the task
        short newPriority = (short) 2;
        task.setPriority(newPriority);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/task/priority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn();


        // Verify that the task has the updated priority
        Task updatedTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertEquals(newPriority, updatedTask.getPriority());
    }

    @Test
    void updateTaskMaturityDateById() throws Exception {
        // Create a task
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority((short) 1);
        task.setStatus("To do");
        task.setMaturityDate(LocalDate.now());
        Task savedTask = taskService.createTask(task);

        // Update the maturity date of the task
        LocalDate newMaturityDate = LocalDate.of(2024, 11, 11);
        task.setMaturityDate(newMaturityDate);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/task/maturity_date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn();


        // Verify that the task has the updated maturity date
        Task updatedTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertEquals(newMaturityDate, updatedTask.getMaturityDate());
    }
}