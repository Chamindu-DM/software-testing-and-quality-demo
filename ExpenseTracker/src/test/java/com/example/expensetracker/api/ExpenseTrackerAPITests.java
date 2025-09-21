package com.example.expensetracker.api;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class ExpenseTrackerAPITests {

    private final String BASE_URL = "http://localhost:8081";

    @Test
    void shouldCreateNewExpenseWithPost() {
        String requestBody = "{ \"description\": \"Dinner\", \"amount\": 45.75 }";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URL + "/api/expenses")
                .then()
                .statusCode(201)
                .body("description", equalTo("Dinner"))
                .body("amount", equalTo(45.75f));
    }

    @Test
    void shouldRetrieveAllExpensesAndFindNewOne() {
        // First, create an expense to ensure it's in the list
        String requestBody = "{ \"description\": \"Coffee\", \"amount\": 5.00 }";
        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_URL + "/api/expenses")
                .then()
                .statusCode(201);

        // Then, retrieve all expenses and validate
        given()
                .when()
                .get(BASE_URL + "/api/expenses")
                .then()
                .statusCode(200)
                .body("description", hasItems("Coffee"));
    }


}