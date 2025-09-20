package cucumber.steps;

import com.testing.taskmanager.entity.Task;
import com.testing.taskmanager.entity.User;
import com.testing.taskmanager.service.TaskService;
import com.testing.taskmanager.service.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for the Task Management BDD features.
 * Connects the Gherkin syntax to the application's service layer.
 */
public class TaskManagementSteps {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    private Task currentTask;
    private User currentUser;
    private boolean isAuthenticated;

    @Given("a user is logged in with username {string} and password {string}")
    public void a_user_is_logged_in(String username, String password) {
        // In a real scenario, this would involve calling a login endpoint.
        // For this BDD test, we'll simulate the service call.
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.createUser(user); // Mocking user creation for the test
        isAuthenticated = userService.authenticateUser(username, password).isPresent();
        assertTrue(isAuthenticated, "User should be authenticated.");
    }

    @Given("a new task exists with title {string} and status {string}")
    public void a_new_task_exists(String title, String status) {
        currentTask = new Task();
        currentTask.setTitle(title);
        currentTask.setStatus(status);
    }

    @Given("a user with username {string} and password {string} is registered")
    public void a_user_is_registered(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("user");
        userService.createUser(user);
    }

    @When("the user tries to log in with username {string} and password {string}")
    public void the_user_tries_to_log_in(String username, String password) {
        isAuthenticated = userService.authenticateUser(username, password).isPresent();
    }

    @When("the user attempts to add the task")
    public void the_user_attempts_to_add_the_task() {
        assertNotNull(currentTask);
        currentTask = taskService.createTask(currentTask);
    }

    @When("the user enters task details with a title {string} and description {string}")
    public void the_user_enters_task_details(String title, String description) {
        currentTask = new Task();
        currentTask.setTitle(title);
        currentTask.setDescription(description);
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {
        assertTrue(isAuthenticated, "Login should be successful.");
    }

    @Then("the login should fail")
    public void the_login_should_fail() {
        assertFalse(isAuthenticated, "Login should fail.");
    }

    @Then("the task should be created successfully")
    public void the_task_should_be_created_successfully() {
        assertNotNull(currentTask.getId());
        assertEquals("New Task", currentTask.getTitle());
    }

    @Then("the task should not be created due to invalid data")
    public void the_task_should_not_be_created_due_to_invalid_data() {
        assertNull(currentTask);
    }
}
