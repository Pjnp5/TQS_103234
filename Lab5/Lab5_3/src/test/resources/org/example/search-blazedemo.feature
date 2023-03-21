Feature: Search in BlazeDemo

  Scenario: Buying tickets for a flight
    When I open the driver and navigate to 'https://www.blazedemo.com/'
    And I going from 'Philadelphia' to 'London'
    And I enter my data, "Sou", "123 coisa", "wubba", "lubba", "9192"
    And I enter my card data, "American Express", "9182", "12", "2022", "Mr G"

    Then I assert the website title,"BlazeDemo Confirmation", close the driver