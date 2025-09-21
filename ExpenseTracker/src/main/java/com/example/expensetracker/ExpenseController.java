// src/main/java/com/example/expensetracker/ExpenseController.java
package com.example.expensetracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final List<Expense> expenses = new ArrayList<>();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        if (expense.getAmount() < 0) {
            throw new IllegalArgumentException("Amount cannot be a negative value");
        }
        expenses.add(expense);
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Expense> getAllExpenses() {
        return expenses;
    }
}
