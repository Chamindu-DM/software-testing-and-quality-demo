// src/main/java/com/example/expensetracker/ExpenseController.java

package com.example.expensetracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final List<Expense> expenses = new ArrayList<>();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        // This validation is a key part of security to prevent malicious data.
        if (expense.getAmount() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot be a negative value");
        }

        expenses.add(expense);
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Expense> getAllExpenses() {
        // This method does not use any user input in a database query,
        // making it safe from SQL Injection.
        return expenses;
    }

}