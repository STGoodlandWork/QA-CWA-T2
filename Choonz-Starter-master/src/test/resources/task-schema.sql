drop table if exists `genre` CASCADE;
drop table if exists `artist` CASCADE;
drop table if exists `album` CASCADE;
drop table if exists `track` CASCADE;
drop table if exists `playlist` CASCADE;

 
 
 
create table if not exists genre (id int PRIMARY KEY AUTO_INCREMENT, `name` varchar(255), `description` varchar(255) );
create table if not exists artist(id int PRIMARY KEY AUTO_INCREMENT, `name` varchar(255) not null);
create table if not exists album (id int PRIMARY KEY AUTO_INCREMENT, `name` varchar(255), `artist_id` int, `cover` varchar(255), genre_id int );
create table if not exists track (id int PRIMARY KEY AUTO_INCREMENT, `title` varchar(255), `artist_id` int, `album_id` int, `duration` int, `lyrics` varchar(255), `genre_id` int, `playlist_id` int);
create table if not exists playlist (id int PRIMARY KEY AUTO_INCREMENT, `name` varchar(255, `description` varchar(255), `artwork` varchar(255));