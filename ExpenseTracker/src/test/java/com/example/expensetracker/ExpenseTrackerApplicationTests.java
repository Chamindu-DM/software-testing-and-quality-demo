// src/test/java/com/example/expensetracker/ExpenseTrackerApplicationTests.java

package com.example.expensetracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpenseTrackerApplicationTests {

    // Note: You would need to autowire or instantiate your controller or service here
    // private final ExpenseController expenseController = new ExpenseController();

    @Test
    void contextLoads() {
        // This test ensures the Spring application context loads correctly
    }

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        // Create an instance of the controller to test the method
        ExpenseController controller = new ExpenseController();
        Expense negativeExpense = new Expense("Negative Item", -10.0);

        // Assert that the specified exception is thrown when the createExpense method is called
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.createExpense(negativeExpense);
        });
    }

}