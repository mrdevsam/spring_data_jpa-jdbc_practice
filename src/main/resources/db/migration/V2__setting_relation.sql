alter table book
    add constraint book_author_fk foreign key (author_id) references author (id);
