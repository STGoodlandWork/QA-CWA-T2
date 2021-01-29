Feature: Test CRUD on Track
As a user
I want to access Choonz
So that I can Create, Read, Update and Delete a Track

Scenario: Test CRUD on Track
Given I access the choonz frontpage to test Track
When I create a track
And I search for it by track name
Then I can update and delete the track