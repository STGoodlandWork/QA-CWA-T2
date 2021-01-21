INSERT INTO `genre` (`name`, `description`) VALUES('Indie Folk', 'whiny-voiced white sadboi music');
INSERT INTO `genre` (`name`, `description`) VALUES('Indie Rock and Roll', 'straight bangers from your youth');

INSERT INTO `playlist`(`name`, `description`, `artwork`) VALUES('Sadbois', 'whiny musics', 'some url');
INSERT INTO `playlist`(`name`, `description`, `artwork`) VALUES('Happybois', 'less whiny musics', 'some other url');


INSERT INTO `artist` (`name`) VALUES('The Weeknd');
INSERT INTO `artist` (`name`) VALUES('Daniel Caesar');
INSERT INTO `artist` (`name`) VALUES('SZA');
INSERT INTO `artist` (`name`) VALUES('Brent Faiyaz');
INSERT INTO `artist` (`name`) VALUES('Mac Miller');

INSERT INTO `album`(`name`, `artist_id`, `cover`, `genre_id`) VALUES('We Shall All Be Healed', null, null, null);
INSERT INTO `album`(`name`, `artist_id`, `cover`, `genre_id`) VALUES('Tallahassee', null, null, null);
INSERT INTO `album`(`name`, `artist_id`, `cover`, `genre_id`) VALUES('In League With Dragons', null, null, null);
INSERT INTO `album`(`name`, `artist_id`, `cover`, `genre_id`) VALUES('The Sunset Tree', null, null, null);
INSERT INTO `album`(`name`, `artist_id`, `cover`, `genre_id`) VALUES('These Four Walls', null, null, null);


INSERT INTO `track` (`title`, `album_id`, `duration`, `lyrics`, `genre_id`, `playlist_id`) values('Cotton', null, 360, 'This song is for the rats...', 1, 1);
INSERT INTO `track` (`title`, `album_id`, `duration`, `lyrics`, `genre_id`, `playlist_id`) VALUES ('Oceanographers Choice', null, 400, 'WELL...', 1, 1);
INSERT INTO `track` (`title`, `album_id`, `duration`, `lyrics`, `genre_id`, `playlist_id`) VALUES ('Younger', null, 500, 'Crank that siren high...', 1, 1);
INSERT INTO `track` (`title`, `album_id`, `duration`, `lyrics`, `genre_id`, `playlist_id`) VALUES ('Broom People', null, 360, '36 Hudson in the garage...', 1, 1);
INSERT INTO `track` (`title`, `album_id`, `duration`, `lyrics`, `genre_id`, `playlist_id`) VALUES ('This Is My House, This Is My Home', null, 545, 'Woke when it was dark...', 2, 2);

COMMIT;