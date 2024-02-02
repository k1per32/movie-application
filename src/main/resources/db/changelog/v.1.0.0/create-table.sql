-- liquibase formatted sql
-- changeset k1per32:2
CREATE SCHEMA if not exists movie_application;

CREATE TABLE if not exists movie_application.users
(
    id_users serial,
    email    varchar unique,
    username varchar,
    name     varchar,
    password varchar,
    enabled  boolean,
    PRIMARY KEY (id_users)
);

CREATE TABLE if not exists movie_application.movies
(
    id_movies serial,
    name      varchar,
    url       varchar,
    PRIMARY KEY (id_movies)
);

CREATE TABLE if not exists movie_application.favourites
(
    id            serial,
    id_users_fav  integer,
    id_movies_fav integer,
    PRIMARY KEY (id),
    FOREIGN KEY (id_users_fav) REFERENCES movie_application.users ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_movies_fav) REFERENCES movie_application.movies ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE if not exists movie_application.authorities
(
    username  VARCHAR(50),
    authority VARCHAR(50),
    FOREIGN KEY (username) REFERENCES movie_application.users (username) ON UPDATE CASCADE ON DELETE CASCADE
);