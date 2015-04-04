
DROP TABLE if EXISTS film_salle;
DROP TABLE if EXISTS salle;

CREATE TABLE salle (
	id BIGINT PRIMARY KEY,
	name VARCHAR(255),
	city VARCHAR(255),
	zipcode VARCHAR(255),
	departement VARCHAR(255)
);

CREATE TABLE film_salle (
	salle_id BIGINT,
	film_id VARCHAR(255)
);

alter table film_salle add constraint fk_salle_id foreign key (salle_id) references public.salle;

ALTER TABLE film_salle ADD CONSTRAINT uk_film_salle UNIQUE(salle_id, film_id);

INSERT INTO salle (id, name, city, zipcode, departement) VALUES
(1, "Utopia", "Toulouse", "31000", "31"),
(2, "Moulin du Roc", "", "Niort", "79000", "79"),
(3, "Pathé", "Prahecq", "79512", "79"),
(4, "", "", "", ""),
(5, "", "", "", ""),
(6, "", "", "", ""),
(7, "", "", "", ""),
(8, "", "", "", ""),
(9, "", "", "", ""),
(10, "", "", "", ""),
(11, "", "", "", ""),
(12, "", "", "", ""),
(13, "", "", "", ""),
(14, "", "", "", ""),
(15, "", "", "", ""),
(16, "", "", "", ""),
(17, "", "", "", ""),
(18, "", "", "", ""),
(19, "", "", "", ""),
(20, "", "", "", "");
