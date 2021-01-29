Feature: Test CRUD on Album
As a user
I want to access Choonz
So that I can Create, Read, Update and Delete an Album

Scenario: Test CRUD on Album
Given I access the choonz frontpage
When I create an album
And I search for it by album name
Then I can update and delete it