language: en
@tag_for_ci
Feature: Task Management System Functionality

  As a user of the task management system
  I want to be able to manage my tasks
  So that I can stay organized

  Scenario: Successful user login
    Given a user with username "testuser" and password "password123" is registered
    When the user tries to log in with username "testuser" and password "password123"
    Then the login should be successful

  Scenario: Failed user login with incorrect password
    Given a user with username "testuser" and password "password123" is registered
    When the user tries to log in with username "testuser" and password "wrongpassword"
    Then the login should fail

  Scenario: Successfully add a new task
    Given a user is logged in with username "testuser" and password "password123"
    And a user enters task details with a title "New Task" and description "This is a test task"
    When the user attempts to add the task
    Then the task should be created successfully

  Scenario: Task validation fails for invalid title
    Given a user is logged in with username "testuser" and password "password123"
    And a user enters task details with a title "" and description "This is an invalid task"
    When the user attempts to add the task
    Then the task should not be created due to invalid data