DROP DATABASE IF EXISTS wordRelations;
CREATE DATABASE wordRelations;
USE wordRelations;

CREATE TABLE words
(
	id 				INT NOT NULL UNIQUE AUTO_INCREMENT,
    word 			VARCHAR(102) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE urls
(
	id INT NOT NULL UNIQUE AUTO_INCREMENT,
    url VARCHAR(102) UNIQUE NOT NULL,
    primary key (id)
);

Create table words_in_url(
	id INT NOT NULL UNIQUE AUTO_INCREMENT,
	word_id int not null,
    url_id int not null,
    primary key(id),
    foreign key (word_id) REFERENCES  words (id) on delete cascade,
    foreign key (url_id) references urls(id) on delete cascade
);


INSERT INTO words(word) VALUES('hello');
Insert into words(word) Values('world');
Insert into words(word) values('I\'m');
Insert into words(word) Values('testing ');
Insert into words(word) Values('my');
Insert into words(word) Values('file');
Insert into words(word) Values('lonely');

Insert into urls(url) Values('url1');
Insert into urls(url) Values('url2');
Insert into urls(url) Values('url3');

insert into words_in_url(word_id, url_id) values (1,1);
insert into words_in_url(word_id, url_id) values (2,1);
insert into words_in_url(word_id, url_id) values (1,2);
insert into words_in_url(word_id, url_id) values (1,2);
insert into words_in_url(word_id, url_id) values (3,2);
insert into words_in_url(word_id, url_id) values (4,2);
insert into words_in_url(word_id, url_id) values (5,2);
insert into words_in_url(word_id, url_id) values (6,2);
insert into words_in_url(word_id, url_id) values (7,3);



