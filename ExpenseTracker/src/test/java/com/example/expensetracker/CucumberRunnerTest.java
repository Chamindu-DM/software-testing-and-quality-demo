package com.example.expensetracker;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example.expensetracker.stepdefinitions", // You'll need to create this package and the step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberRunnerTest {
    // just a runner.
}