package unit;

import com.testing.taskmanager.entity.Task;
import com.testing.taskmanager.repository.TaskRepository;
import com.testing.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TDD-style unit tests for the TaskService.
 * Follows the Red-Green-Refactor cycle.
 *
 */
@DisplayName("TaskService Unit Tests (TDD)")
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TDD Cycle 1: Test creating a valid task.
     *
     * Red: Write a test that fails because the functionality doesn't exist yet.
     * Green: Write just enough code in the service to make the test pass.
     * Refactor: Clean up the code and tests.
     */
    @Test
    @DisplayName("should create a valid task")
    public void testCreateValidTask_Success() {
        // Red - Test for a new task creation
        Task newTask = new Task();
        newTask.setTitle("TDD Test Task");
        newTask.setDescription("A task for TDD testing.");

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        // Green - Run the test and see it pass
        Task createdTask = taskService.createTask(newTask);

        assertNotNull(createdTask);
        assertEquals("TDD Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(newTask);
    }

    /**
     * TDD Cycle 2: Test task input validation (negative case).
     *
     * Red: Write a test that fails when an invalid task is passed.
     * Green: Implement the validation logic in the service.
     * Refactor: The validation logic is simple, so no major refactoring is needed.
     */
    @Test
    @DisplayName("should not create a task with invalid title")
    public void testCreateTask_InvalidTitle_ReturnsNull() {
        // Red - Test for invalid task creation (e.g., empty title)
        Task invalidTask = new Task();
        invalidTask.setTitle("");

        // Green - Run the test and see it pass
        Task createdTask = taskService.createTask(invalidTask);

        assertNull(createdTask);
        verify(taskRepository, never()).save(any(Task.class));
    }

    /**
     * TDD Cycle 3: Test task input validation for XSS vulnerability.
     *
     * Red: Write a test that fails if a task with malicious script is created.
     * Green: Implement the `validateTaskInput` method with sanitization logic.
     * Refactor: Ensure the validation logic is clean and reusable.
     */
    @Test
    @DisplayName("should prevent XSS attacks in task title")
    public void testValidateTaskInput_XSSAttack_ReturnsFalse() {
        // Red - Test for XSS in the task title
        Task xssTask = new Task();
        xssTask.setTitle("<script>alert('xss')</script>");
        xssTask.setDescription("Legit description");

        // Green - Run the test and see it pass after validation is implemented
        boolean isValid = taskService.validateTaskInput(xssTask);

        assertFalse(isValid);
    }
}
