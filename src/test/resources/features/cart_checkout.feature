@regression
Feature: Cart and checkout

  Background:
    Given I am logged in as a standard user

  @smoke
  Scenario: Add product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1" item

  Scenario Outline: Checkout with various details
    When I add "Sauce Labs Backpack" to the cart
    And I go to the cart
    And I proceed to checkout
    And I enter checkout details from "checkout_data.csv" for test "<test_id>"
    Then I should see the expected checkout result

    Examples:
      | test_id |
      | CH01    |
      | CH02    |
      | CH03    |
      | CH04    |