Feature: Test View functions on entities
As a user
I want to access Choonz
So that I can view 

Scenario: Test CRUD on Track
Given I access the choonz frontpage to view artists
When I view an artist
And I check for its tracks and albums
Then I can also view the related tracks and albums