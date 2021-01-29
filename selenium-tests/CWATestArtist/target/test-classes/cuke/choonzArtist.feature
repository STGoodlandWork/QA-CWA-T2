Feature: Test CRUD on Artist
As a user
I want to access Choonz
So that I can Create, Read, Update and Delete an Artist

Scenario: Test CRUD on Album
Given I access the choonz frontpage to test Artist
When I create an artist
And I search for it by artist name
Then I can update and delete the artist