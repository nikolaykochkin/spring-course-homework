INSERT INTO AUTHORS (ID, NAME)
VALUES (1, 'Leo Tolstoy');
INSERT INTO AUTHORS (ID, NAME)
VALUES (2, 'Fyodor Dostoevsky');
INSERT INTO AUTHORS (ID, NAME)
VALUES (3, 'William Shakespeare');
INSERT INTO AUTHORS (ID, NAME)
VALUES (4, 'Test Test');
INSERT INTO GENRES (ID, NAME)
VALUES (1, 'Novel');
INSERT INTO GENRES (ID, NAME)
VALUES (2, 'Poem');
INSERT INTO BOOKS (ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (1, 'War and Peace', 1, 1);
INSERT INTO BOOKS (ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (2, 'Crime and Punishment', 2, 1);
INSERT INTO BOOKS (ID, NAME, AUTHOR_ID, GENRE_ID)
VALUES (3, 'Venus and Adonis', 3, 2);