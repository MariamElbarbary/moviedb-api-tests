# Created by mariamelbarbary at 10/12/2022
Feature: Tests for GET /movie/top_rated
  # Enter feature description here

  Scenario: Happy flow for GET /movie/top_rated
    Given API url is https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    When  User send a GET request with valid api key
    Then  Response status is 200  Scenario: Happy flow for GET /movie/top_rated

  Scenario:  GET /movie/top_rated unauthorized
    Given API url is https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    When  User send a GET request with invalid api key
    Then  Response status is 401
    And   response body  contains  "status_code": 7
    And response body contains   "status_message": "Invalid API key: You must be granted a valid key.",


 Scenario:  GET /movie/top_rated resource not found
    Given API url is https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    When  User send a GET request with valid api key
   And    Send a query parameter page = 2000 <invalid>
    Then  Response status is 401
    And   response body  contains  "status_code": 7
    And response body contains   "status_message": "Invalid API key: You must be granted a valid key.",



  Scenario:  GET /movie/top_rated has valid json schema returned
  Scenario:  GET /movie/top_rated has valid validate the body response has the correct value
  Scenario:  GET /movie/top_rated has Query parameter page =1000 should return 200
  Scenario:  GET /movie/top_rated has Query parameter page =1001 should fail not defined in the api specs
  Scenario:  GET /movie/top_rated has Query parameter language-ar-EG should work and verify the strings are translated and not messed up  \\ which is   bug as well
  Scenario:  GET /movie/top_rated has Query parameter language-validation  for the regular expression ([a-z]{2}-[A-Z]{2})
  Scenario:  GET /movie/top_rated has Query parameter region-validation  for the regular expression ^[A-Z]{2}$
  Scenario:  GET /movie/top_rated important fields are returned with values and correct data types


  Scenario:  POST /movie/{movie_id}/rating with guest sessionID
  Scenario:  POST /movie/{movie_id}/rating with invalid guest sessionID
  Scenario:  POST /movie/{movie_id}/rating with Valid User session ID
  Scenario:  POST /movie/{movie_id}/rating with inValid User session ID
  Scenario:  POST /movie/{movie_id}/rating without guest userID or Valid User session ID
  Scenario:  POST /movie/{movie_id}/rating wit valid user ID and invalid APIKEY
  Scenario:  POST /movie/{movie_id}/rating with an invalid JSON body
  Scenario:  POST /movie/{movie_id}/rating with the different values for the JSON body ( boundary analysis)
  Scenario:  POST /movie/{movie_id}/rating with invalid movieId




