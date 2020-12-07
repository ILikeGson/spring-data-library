-->DROP TABLE book_to_author;
-->DROP table book;
-->DROP table author;
-->DROP table genre;


INSERT INTO author(first_name, last_name)
VALUES  ('Joanne', 'Rowling'),
        ('George', 'Martin'),
        ('Conan', 'Doyle');

INSERT INTO genre(genre)
VALUES  ('fantasy'),
        ('detective');

INSERT INTO book(title, author_id, genre_id)
VALUES  ('Harry Potter and the Sorcerer’s Stone',  1, 1),
        ('Harry Potter and the Chamber of Secrets',  1, 1),
        ('Harry Potter and the Prisoner of Azkaban', 1, 1),
        ('A Game of Thrones', 2, 1),
        ('A Clash of Kings', 2, 1),
        ('A Storm of Swords', 2, 1),
        ('The Mystery of Sasassa Valley', 3, 2),
        ('Sherlock Holmes in Real Life', 3, 2);

INSERT INTO book_comment(comment, book_id)
VALUES  ('Good one', 1),
        ('I love this', 1),
        ('Story from my childhood', 2),
        ('As always very good', 2),
        ('The best part in my opinion', 3),
        ('Love the universe', 4),
        ('Waiting when George Martin finnish his unreleased books', 5),
        ('So-so', 6),
        ('The best detective story', 7),
        ('Sherlock Holmes in real life the same that he is in the books', 8)