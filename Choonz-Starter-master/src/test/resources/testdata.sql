INSERT INTO `genre` (`name`, `description`) VALUES('Rap', 'Rhymthm and Poetry music');
INSERT INTO `genre` (`name`, `description`) VALUES('Pop', 'Music for the Radio');
INSERT INTO `genre` (`name`, `description`) VALUES('Country', 'Lil Nas X created this');
INSERT INTO `genre` (`name`, `description`) VALUES('House', 'Good Vibes Music');
INSERT INTO `genre` (`name`, `description`) VALUES('R&B', 'Heartbroken people love this');

INSERT INTO `playlist`(`name`, `description`, `artwork`) VALUES('The Weeknd', 'Best of The Weeknd', 'some url');
INSERT INTO `playlist`(`name`, `description`, `artwork`) VALUES('Pop songs', 'best pop songs', 'cool artwork');
INSERT INTO `playlist`(`name`, `description`, `artwork`) VALUES('80s songs', 'best 80s songs', 'cool artwork');


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


INSERT INTO `track` (`title`, `duration`, `lyrics`) values('Good Days', 120, 'Laurem Epsom');
INSERT INTO `track` (`title`, `duration`, `lyrics`) VALUES ('We Hate Git', 120, 'Laurem Epsom');
INSERT INTO `track` (`title`, `duration`, `lyrics`) VALUES ('Ringtone', 120, 'Laurem Epsom');
INSERT INTO `track` (`title`, `duration`, `lyrics`) VALUES ('git push -f saved my life', 120, 'Laurem Epsom');

COMMIT;