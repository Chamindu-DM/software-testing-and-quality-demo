package com.example.expensetracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpenseTrackerApplicationTests {

    @Test
    void shouldThrowExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Expense("Groceries", -50.0);
        });
    }
}