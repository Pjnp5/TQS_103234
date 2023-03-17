Feature: Basic Arithmetic
  Background: A Calculator
    Given Calculator initiated
  Scenario: Addition
    When I add 4 and 5
    Then the result is 9
  Scenario: Substraction
    When I subtract 7 to 2
    Then the result is 5
  Scenario: Multiplication
    When I multiply 3 by 2
    Then the result is 6
  Scenario: Divison
    When I divide 8 by 4
    Then the result is 2
  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>
    Examples: Single digits
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |
  Scenario Outline: Several Multiplications
    When I multiply <a> by <b>
    Then the result is <c>
    Examples: Single digits
      | a | b | c  |
      | 6 | 2 | 12 |
      | 3 | 7 | 21 |