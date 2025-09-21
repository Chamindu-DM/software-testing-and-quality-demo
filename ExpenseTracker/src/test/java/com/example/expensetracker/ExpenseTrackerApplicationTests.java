// src/test/java/com/example/expensetracker/ExpenseTrackerApplicationTests.java

package com.example.expensetracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class ExpenseTrackerApplicationTests {

    // You would typically autowire the controller, but for this simple test,
    // we can instantiate it directly.
    private final ExpenseController expenseController = new ExpenseController();

    @Test
    void contextLoads() {
        // This test ensures the Spring application context loads correctly
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        // Create an instance of the expense with a negative amount
        Expense negativeExpense = new Expense("Negative Item", -10.0);

        // Assert that a ResponseStatusException is thrown when the createExpense method is called
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            expenseController.createExpense(negativeExpense);
        });
    }

}