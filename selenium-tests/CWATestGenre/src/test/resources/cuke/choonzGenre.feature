Feature: Test CRUD on Genre
As a user
I want to access Choonz
So that I can Create, Read, Update and Delete a Genre

Scenario: Test CRUD on Genre
Given I access the choonz frontpage to test Genre
When I create a genre
And I search for it by genre name
Then I can update and delete the genre