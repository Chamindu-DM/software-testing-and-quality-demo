package com.testing.taskmanager.controller;

import com.testing.taskmanager.entity.Task;
import com.testing.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * REST controller for Task-related operations.
 *
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Endpoint to create a new task.
     *
     * @param task The task object to create.
     * @return A ResponseEntity with the created task.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            if (createdTask != null) {
                LOGGER.info("Task created successfully with ID: " + createdTask.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            LOGGER.severe("Task creation error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to retrieve all tasks.
     *
     * @return A ResponseEntity with a list of all tasks.
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.findAll();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            LOGGER.severe("Error retrieving tasks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint to retrieve a task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return A ResponseEntity with the task if found, or a 404 Not Found error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Optional<Task> task = taskService.findById(id);
            return task.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            LOGGER.severe("Error retrieving task by ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
