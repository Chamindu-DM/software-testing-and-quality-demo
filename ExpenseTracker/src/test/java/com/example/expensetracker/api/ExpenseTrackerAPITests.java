// src/test/java/com/example/expensetracker/api/ExpenseTrackerAPITests.java

package com.example.expensetracker.api;

import com.example.expensetracker.Expense;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class ExpenseTrackerAPITests {

    private static final String BASE_URL = "http://localhost:8081/api/expenses";
    private static final String GENERATED_PASSWORD = "user";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void shouldCreateNewExpenseWithPost() {
        Expense newExpense = new Expense("Groceries", 50.0);
        given().log().all()
                .auth().preemptive().basic("user", GENERATED_PASSWORD)
                .contentType(ContentType.JSON)
                .body(newExpense)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldRetrieveAllExpensesAndFindNewOne() {
        // Create an expense first
        Expense firstExpense = new Expense("Rent", 1200.0);
        given().log().all()
                .auth().preemptive().basic("user", GENERATED_PASSWORD)
                .contentType(ContentType.JSON)
                .body(firstExpense)
                .when()
                .post();

        // Retrieve all expenses and check for the one we just created
        given().log().all()
                .auth().preemptive().basic("user", GENERATED_PASSWORD)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("description", hasItem("Rent"))
                .body("amount", hasItem(1200.0f));
    }
}