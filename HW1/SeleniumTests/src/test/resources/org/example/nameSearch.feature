Feature: City Air quailty search
  To allow a client to search for a city air quality.

  Scenario: Search by Name
    Given a website 'http://127.0.0.1:3000/'
    Then inital cache values are checked
    Then a city with name 'Aveiro' is searched
    Then the recieved values for the name and the cache values are checked
    Then the city is searched again
    Then the name cache values are checked again
    Then the website is closed


  Scenario: Search by Latitude and Longitude
    Given a website 'http://127.0.0.1:3000/'
    Then inital cache values are checked
    Then a city latitute '41.15' and longitude '-8.62' is searched
    Then the recieved values for the coords and the cache values are checked
    Then the city is searched again
    Then the coords cache values are checked again
    Then the website is closed