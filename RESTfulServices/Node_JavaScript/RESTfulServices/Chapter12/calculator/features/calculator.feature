Feature: Calculator
    As a user
    I want to use a calculator to do math
    So I get more accurate answers than if I did the math manually

    Scenario: Add 2 and 3
        Given I have a calculator
        When I add 2 and 3
        Then the result should be 5

    # Scenario Outline: Add two numbers <num1> and <num2>
    #     Given I have a calculator
    #     When I add <num1> and <num2>
    #     Then the result should be <total>

    #     Examples:
    #         | num1 | num2 | total |
    #         | 2    | 3    | 5     |
    #         | -2   | 3    | 1     |
    #         | 10   | 15   | 25    |
    #         | 99   | -99  | 0     |
    #         | -1   | -10  | -11   |
    #         | 0    | 0    | 0     |
    #         | 0    | 5    | 5     |
    #         | 7    | 0    | 7     |
    #         | -3   | 0    | -3    |
