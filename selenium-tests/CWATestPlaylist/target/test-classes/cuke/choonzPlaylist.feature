Feature: Test CRUD on Playlist
As a user
I want to access Choonz
So that I can Create, Read, Update and Delete a Playlist

Scenario: Test CRUD on Playlist
Given I access the choonz frontpage to test Playlist
When I create a playlist
And I search for it by playlist name
Then I can update and delete the playlist