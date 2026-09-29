@regression
Feature: Login functionality

  Background:
    Given I am on the login page

  @smoke
  Scenario: Valid user logs in successfully
    When I login using data from "login_data.csv" for test "TC01"
    Then I should see the expected login result

  Scenario: Locked out user cannot login
    When I login using data from "login_data.csv" for test "TC02"
    Then I should see the expected login result

  Scenario Outline: Invalid login attempts
    When I login using data from "login_data.csv" for test "<test_id>"
    Then I should see the expected login result

    Examples:
      | test_id |
      | TC03    |
      | TC04    |
      | TC05    |