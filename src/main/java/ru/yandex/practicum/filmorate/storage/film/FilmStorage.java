package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film addFilm(Film film);

    Optional<Film> updateFilm(Film modifiedFilm);

    Film deleteFilm(Long filmId);

    Optional<Film> getFilmById(Long filmId);

    Collection<Film> getAllFilms();

    void addLike(long userId, long filmId);

    void deleteLike(long userId, long filmId);

    Collection<Film> getPopularFilms(long filmCount);
}
