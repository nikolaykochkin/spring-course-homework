insert into authors (id, name)
values (1, 'Leo Tolstoy');
insert into authors (id, name)
values (2, 'Fyodor Dostoevsky');
insert into authors (id, name)
values (3, 'William Shakespeare');
insert into authors (id, name)
values (4, 'Test Author');
insert into genres (id, name)
values (1, 'Novel');
insert into genres (id, name)
values (2, 'Poem');
insert into genres (id, name)
values (3, 'Test Genre');
insert into books (id, name, author_id, genre_id)
values (1, 'War and Peace', 1, 1);
insert into books (id, name, author_id, genre_id)
values (2, 'Crime and Punishment', 2, 1);
insert into books (id, name, author_id, genre_id)
values (3, 'Venus and Adonis', 3, 2);
insert into comments (id, created_at, text, book_id)
values (1, current_timestamp(), 'Cool!', 2);
insert into comments (id, created_at, text, book_id)
values (2, current_timestamp(), 'Awesome!', 1);
insert into comments (id, created_at, text, book_id)
values (3, current_timestamp(), 'Too long(!', 1);
insert into comments (id, created_at, text, book_id)
values (4, current_timestamp(), 'Test comment', 1);