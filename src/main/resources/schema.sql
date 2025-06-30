CREATE TABLE IF NOT EXISTS rating (
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        varchar(50),
    description varchar(100)
);

CREATE TABLE IF NOT EXISTS genres (
     id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     name varchar(50)
);

CREATE TABLE IF NOT EXISTS films (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        varchar (50) NOT NULL,
    description varchar(200),
    releaseDate date,
    duration    integer,
    rating_id   INTEGER REFERENCES rating (id)
);

CREATE TABLE IF NOT EXISTS films_genres (
     id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     film_id  BIGINT REFERENCES films (id),
     genre_id INTEGER REFERENCES genres (id)
);

CREATE TABLE IF NOT EXISTS users (
     id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     email    varchar(100),
     login    varchar(50) NOT NULL,
     name     varchar(200) NOT NULL,
     birthday date
);

CREATE TABLE IF NOT EXISTS likes (
     id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     film_id BIGINT REFERENCES films (id),
     user_id BIGINT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS friends (
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id),
    friend_id BIGINT REFERENCES users (id),
    status    bool
);
