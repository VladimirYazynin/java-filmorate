package ru.yandex.practicum.filmorate.dal.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {
    Film addFilm(Film film);

    void updateFilm(Film modifiedFilm);

    void deleteFilm(Long filmId);

    Optional<Film> getFilmById(Long filmId);

    Collection<Film> getAllFilms();

    void addLike(long userId, long filmId);

    void deleteLike(long userId, long filmId);

    Collection<Film> getPopularFilms(long filmCount);

    void insertGenreForFilm(long idFilm, Set<Genre> list);
}
