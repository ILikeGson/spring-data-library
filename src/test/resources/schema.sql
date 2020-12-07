DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;

CREATE TABLE author
(
	id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR(128) NOT NULL,
	last_name VARCHAR(128) NOT NULL
);

CREATE TABLE genre
(
	id BIGSERIAL PRIMARY KEY,
	genre VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE book
(
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR(128) NOT NULL UNIQUE,
	author_id BIGINT references author(id),
	genre_id BIGINT references genre(id)
);

CREATE TABLE book_comment
(
    id BIGSERIAL PRIMARY KEY,
    comment text NOT NULL,
    book_id BIGINT references book(id)
);
