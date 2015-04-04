
DROP TABLE if EXISTS film_salle;
DROP TABLE if EXISTS film;
DROP TABLE if EXISTS salle;

CREATE TABLE film (
	imdbID VARCHAR(255) PRIMARY KEY,
	title VARCHAR(255),
	year VARCHAR(255),
	rated VARCHAR(255),
	released VARCHAR(255),
	runtime VARCHAR(255),
	genre VARCHAR(255),
	director VARCHAR(255),
	writer VARCHAR(255),
	actors VARCHAR(255),
	plot CLOB,
	language VARCHAR(255),
	country VARCHAR(255),
	awards VARCHAR(255),
	poster VARCHAR(255),
	metascore VARCHAR(255),
	imdbRating VARCHAR(255),
	imdbVotes VARCHAR(255),
	ftype VARCHAR(255)
);

CREATE TABLE salle (
	id BIGINT PRIMARY KEY,
	name VARCHAR(255),
	address VARCHAR(255),
	city VARCHAR(255),
	zipcode VARCHAR(255),
);

CREATE TABLE film_salle (
	film_id VARCHAR(255) FOREIGN KEY (film_id) REFERENCES film(imdbID) ON UPDATE CASCADE ON DELETE CASCADE,
	salle_id BIGINT FOREIGN KEY (salle_id) REFERENCES salle(id) ON UPDATE CASCADE ON DELETE CASCADE
);

/*
INSERT INTO film VALUES
*/