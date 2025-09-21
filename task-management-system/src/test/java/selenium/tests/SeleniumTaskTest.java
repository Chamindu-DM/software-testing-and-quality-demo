package selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.pages.DashboardPage;
import selenium.pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium UI automation tests for login and task creation.
 * Note: These tests assume a simple UI is running on a specific port.
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("Disabled until UI is properly configured with test environment")
public class SeleniumTaskTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;

    @BeforeAll
    public static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI environments
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setupPages() {
        // Assume the application is running at http://localhost:8080
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Should successfully log in with valid credentials")
    public void testSuccessfulLogin() {
        // This test requires a user to be pre-registered, which would happen in a setup script.
        loginPage.login("testuser", "password123");
        // We'll assert that the URL changes to the dashboard or a specific element appears.
        assertTrue(driver.getCurrentUrl().contains("/dashboard"), "Login should redirect to the dashboard page.");
    }

    @Test
    @Order(2)
    @DisplayName("Should add a new task after logging in")
    public void testAddNewTask() {
        // First, log in as a prerequisite for adding a task
        loginPage.login("testuser", "password123");

        // Add a new task
        String taskTitle = "Selenium UI Test Task";
        dashboardPage.addTask(taskTitle, "A task added via Selenium.");

        // Assert that the task is visible on the page
        assertTrue(dashboardPage.isTaskVisible(taskTitle), "The new task should be visible on the dashboard.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
