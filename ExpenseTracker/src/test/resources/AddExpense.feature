Feature: Add and track expenses
  As a user
  I want to add new expenses to my tracker
  So that I can monitor my spending

  Scenario: User adds a new expense successfully
    Given I am on the home page of the Expense Tracker
    When I fill in "description" with "Groceries"
    And I fill in "amount" with "50.00"
    And I click the "Add Expense" button
    Then I should see "Groceries" in my list of expenses
    And the total balance should be updated accordingly