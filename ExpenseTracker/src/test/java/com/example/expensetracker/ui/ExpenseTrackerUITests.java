// src/test/java/com/example/expensetracker/ui/ExpenseTrackerUITests.java

package com.example.expensetracker.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.UUID;

public class ExpenseTrackerUITests {

    private final String BASE_URL = "http://localhost:8081";
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        // Ensure a unique user data directory for each test run
        options.addArguments("--user-data-dir=/tmp/chrome-profile-" + UUID.randomUUID());
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldNavigateToAddExpensePage() {
        driver.get(BASE_URL + "/add-expense.html");
        assertTrue(driver.getCurrentUrl().contains("/add-expense.html"));
    }

    @Test
    void shouldFindFormElements() {
        driver.get(BASE_URL + "/add-expense.html");
        // Example of an explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("expense-description")));
        assertNotNull(driver.findElement(By.id("expense-description")));
        assertNotNull(driver.findElement(By.id("expense-amount")));
        assertNotNull(driver.findElement(By.id("submit-button")));
    }
}