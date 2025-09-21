package com.example.expensetracker.ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpenseTrackerUITests {

    private final String BASE_URL = "http://localhost:8081";

    @Test
    void shouldNavigateToAddExpensePage() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(BASE_URL + "/add-expense.html");
            assertTrue(driver.getCurrentUrl().contains("/add-expense.html"));
        } finally {
            driver.quit();
        }
    }

    @Test
    void shouldFindFormElements() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get(BASE_URL + "/add-expense.html");
            // Check that the form elements exist on the page
            assertNotNull(driver.findElement(By.id("expense-description")));
            assertNotNull(driver.findElement(By.id("expense-amount")));
            assertNotNull(driver.findElement(By.id("submit-button")));

        } finally {
            driver.quit();
        }
    }
}