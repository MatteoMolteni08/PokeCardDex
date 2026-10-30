DROP IF EXIST pokecarddex_db;
CREATE DATABASE pokecarddex_db;
USE pokecarddex_db;

CREATE TABLE users(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	email VARCHAR(50) UNIQUE,
	nome VARCHAR(50),
	password VARCHAR(50)
);

CREATE TABLE card(
	user_id INTEGER,
	card_id VARCHAR(10),
	quantity INTEGER NOT NULL DEFAULT 1,
	PRIMARY KEY (user_id, card_id),
	FOREIGN KEY (user_id) REFERENCES users(id)
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE pokemon(
	user_id INTEGER,
	pokemon_num INTEGER,
	PRIMARY KEY(user_id, pokemon_num),
	FOREIGN KEY (user_id) REFERENCES users(id)
	ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO card (user_id, card_id, quantity) VALUES 
(2, "swsh3-136", 1),
(3, "sv06-130", 3),
(3, "me02-013", 1),
(2, "sv08.5-041", 3),
(3, "me03-045", 1),
(3, "sv08.5-041", 1),
(3, "sv03-125", 1),
(2, "xy0-26", 1),
(2, "sv09-098", 1),
(2, "sm6-69", 2),
(2, "svp-189", 3),
(2, "svp-184", 2),
(4, "sv08.5-042", 1),
(4, "sv03-115", 1);

SELECT nome, card_id
FROM users, card
WHERE users.id = card.user_id;

INSERT INTO card (user_id, card_id, quantity) VALUES
(3, "me05-031", 1),
(3, "me05-048", 1),
(3, "me03-044", 3);

INSERT INTO card (user_id, card_id, quantity) VALUES
(3, "me02.5-251", 1),
(3, "sv02-271", 1),
(3, "me02.5-154", 2);

INSERT INTO card (user_id, card_id, quantity) VALUES
(3, "sv08.5-071", 4),
(3, "sv08.5-072", 4),
(3, "sv08.5-173", 2);