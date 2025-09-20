package integration;

import com.testing.taskmanager.TaskManagerApplication;
import com.testing.taskmanager.entity.Task;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Integration tests for the Task API using REST Assured.
 *
 * @author Gemini
 * @version 1.0
 */
@SpringBootTest(classes = TaskManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled("Disabled until the API endpoints are fully implemented")
public class TaskApiTest {

    @LocalServerPort
    private int port;

    private String authToken = "test-token"; // Using a mock token for testing

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testCreateTask_Success() {
        Task newTask = new Task();
        newTask.setTitle("API Test Task");
        newTask.setDescription("A task for API automation testing.");
        newTask.setStatus("Pending");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(newTask)
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("API Test Task"))
                .log().all();
    }

    @Test
    public void testGetTaskById_Success() {
        // First, create a task to retrieve
        Task newTask = new Task();
        newTask.setTitle("Task for Retrieval");
        newTask.setDescription("A task to test retrieval endpoint.");
        newTask.setStatus("Pending");

        // Use a real ID from the created task
        Long taskId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(newTask)
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Now, retrieve the created task
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/api/tasks/" + taskId)
                .then()
                .statusCode(200)
                .body("id", equalTo(taskId.intValue()))
                .body("title", equalTo("Task for Retrieval"));
    }

    @Test
    public void testCreateTask_InvalidInput_ReturnsBadRequest() {
        Task invalidTask = new Task();
        invalidTask.setTitle(""); // Invalid title

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(invalidTask)
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(400); // Expect Bad Request
    }

    @Test
    public void testGetTaskById_NotFound_ReturnsNotFound() {
        long nonExistentId = 9999L;

        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/api/tasks/" + nonExistentId)
                .then()
                .statusCode(404); // Expect Not Found
    }
}
