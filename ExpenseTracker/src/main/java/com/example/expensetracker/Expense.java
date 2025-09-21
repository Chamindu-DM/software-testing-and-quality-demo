// src/main/java/com/example/expensetracker/Expense.java
package com.example.expensetracker;

public class Expense {

    private String description;
    private double amount;

    // No-args constructor required for JSON deserialization
    public Expense() {
    }

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
