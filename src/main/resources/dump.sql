
DROP TABLE if EXISTS film_salle;
DROP TABLE if EXISTS salle;

CREATE TABLE salle (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	city VARCHAR(255),
	zipcode VARCHAR(255),
	departement VARCHAR(255)
);

CREATE TABLE film_salle (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    salle_id BIGINT,
	film_id VARCHAR(255)
);

alter table film_salle add constraint fk_salle_id foreign key (salle_id) references public.salle;

ALTER TABLE film_salle ADD CONSTRAINT uk_film_salle UNIQUE(salle_id, film_id);

INSERT INTO salle (id, name, city, zipcode, departement) VALUES
(1, 'UTOPIA', 'TOULOUSE', '31000', '31'),
(2, 'MOULIN DU ROC', 'NIORT', '79000', '79'),
(3, 'PATHE', 'PRAHECQ', '79512', '79'),
(4, 'UGC GAUMONT', 'TOULOUSE', '31000', '31'),
(5, 'ABC', 'TOULOUSE', '31000', '31'),
(6, 'LE CRATERE', 'TOULOUSE', '31500', '31'),
(7, 'MK2 BASTILLE', 'PARIS', '75011', '75'),
(8, 'ENTREPOT', 'PARIS', '75015', '75'),
(9, 'LA COURSIVE', 'LA ROCHELLE', '17000', '17'),
(10, 'STUDIO 43', 'DUNKERQUE', '59140', '59'),
(11, 'MAJESTIC', 'LILLE', '59000', '59'),
(12, 'CINEMA ECRAN 7', 'CUGNAUX', '31270', '31'),
(13, 'UGC CINE CITE', 'BORDEAUX', '33000', '33'),
(14, 'UTOPIA SAINT-SIMON', 'BORDEAUX', '33000', '33'),
(15, 'COLISEE LUMIERE', 'LILLE', '59000', '59');

INSERT INTO film_salle (salle_id, film_id) VALUES
(1, 'tt0138902'),
(1, 'tt0083335'),
(1, 'tt0082503'),
(2, 'tt0138902'),
(2, 'tt0082003'),
(4, 'tt0079396'),
(4, 'tt0082503'),
(4, 'tt0368405'),
(4, 'tt0327997'),
(4, 'tt0083335'),
(5, 'tt0327997'),
(5, 'tt0082003'),
(5, 'tt0079396'),
(6, 'tt0082503'),
(7, 'tt0327997'),
(7, 'tt0138902'),
(8, 'tt0132614'),
(8, 'tt0164354'),
(8, 'tt1645142'),
(9, 'tt0082003'),
(9, 'tt0327997'),
(10, 'tt0138902'),
(10, 'tt0082503'),
(10, 'tt0132614'),
(10, 'tt0327997'),
(11, 'tt0132614'),
(12, 'tt0327997'),
(12, 'tt0083335'),
(12, 'tt0164354'),
(13, 'tt0079396'),
(13, 'tt1645142'),
(13, 'tt0083335'),
(14, 'tt0132614'),
(14, 'tt0368405'),
(14, 'tt0079396'),
(14, 'tt0327997'),
(15, 'tt0083335'),
(15, 'tt1645142'),
(15, 'tt0132614');