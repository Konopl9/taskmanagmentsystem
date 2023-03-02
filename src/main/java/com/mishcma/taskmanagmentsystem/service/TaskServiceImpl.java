package com.mishcma.taskmanagmentsystem.service;

import java.util.List;
import java.util.Optional;

import com.mishcma.taskmanagmentsystem.entity.Task;
import com.mishcma.taskmanagmentsystem.repository.TaskRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    
    public Task getTaskById(Long id) {
        return extractTask(taskRepository.findById(id));
    }

    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Task newTask, Long id) {
        Task unwrappedTask = getTaskById(id);

        if(newTask == null) {
            throw new IllegalArgumentException("Incorrect Task provided");
        }

        Task updatedTask = new Task(
            unwrappedTask.getId(),
            unwrappedTask.getTitle(), 
            unwrappedTask.getDescription(), 
            newTask.getStatus(), 
            unwrappedTask.getCreationDate(),
            unwrappedTask.getUser());
            
        return  taskRepository.save(updatedTask);   

    } 

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private Task extractTask(Optional<Task> task) {
        if(task.isEmpty()) {
            throw new IllegalArgumentException("No task found");
        }
        return task.get();
    }
}
