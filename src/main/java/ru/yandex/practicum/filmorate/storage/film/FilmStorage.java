package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film modifiedFilm);

    Film deleteFilm(Long filmId);

    Film getFilmById(Long filmId);

    Collection<Film> getAllFilms();
}
