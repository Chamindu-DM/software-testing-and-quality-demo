package com.testing.taskmanager.service;

import com.testing.taskmanager.entity.Task;
import com.testing.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Service class for handling Task-related business logic.
 * Includes methods for task creation, retrieval, and validation.
 */
@Service
public class TaskService {
    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Validates task input data to prevent XSS vulnerabilities.
     * Before Fix (Vulnerable to XSS):
     * ```
     * public boolean validateTaskInputVulnerable(Task task) {
     * return task.getTitle() != null && !task.getTitle().isEmpty();
     * }
     * ```
     * After Fix (Secure with input validation and sanitization):
     * We use a regular expression to check for potentially malicious scripts.
     * For a real application, a dedicated library like OWASP Java Sanitizer is recommended.
     *
     * @param task The task object to validate.
     * @return true if the task input is valid, false otherwise.
     */
    public boolean validateTaskInput(Task task) {
        // Simple regex to check for script tags, a common XSS vector
        Pattern scriptPattern = Pattern.compile("<script\\b[^>]*>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        if (task.getTitle() != null && scriptPattern.matcher(task.getTitle()).find()) {
            LOGGER.warning("XSS attack attempt detected in task title.");
            return false;
        }
        if (task.getDescription() != null && scriptPattern.matcher(task.getDescription()).find()) {
            LOGGER.warning("XSS attack attempt detected in task description.");
            return false;
        }

        return task.getTitle() != null && !task.getTitle().trim().isEmpty();
    }

    /**
     * Creates a new task after validating its input.
     *
     * @param task The task object to be created.
     * @return The created Task object, or null if validation fails.
     */
    public Task createTask(Task task) {
        if (!validateTaskInput(task)) {
            LOGGER.severe("Failed to create task: Invalid input.");
            return null;
        }
        return taskRepository.save(task);
    }

    /**
     * Finds a task by its ID.
     *
     * @param id The ID of the task.
     * @return An Optional containing the Task if found.
     */
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Retrieves all tasks.
     *
     * @return A list of all tasks.
     */
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
