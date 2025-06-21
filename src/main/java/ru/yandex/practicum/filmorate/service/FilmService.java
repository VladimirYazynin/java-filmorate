package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film modifiedFilm) {
        return filmStorage.updateFilm(modifiedFilm)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("Фильм с id: %d не найден.", modifiedFilm.getId())
                        ));
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(long userId, long filmId) {
        checkFilmAndUser(userId, filmId);
        filmStorage.addLike(userId, filmId);
    }

    public void deleteLike(long userId, long filmId) {
        checkFilmAndUser(userId, filmId);
        filmStorage.deleteLike(userId, filmId);
    }

    public Collection<Film> getPopularFilms(long filmCount) {
        return filmStorage.getPopularFilms(filmCount);
    }

    private void checkFilmAndUser(long userId, long filmId) {
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Фильм с id: %d не найден.", filmId)
                ));
        userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь с id: %d не найден.", userId)
                ));
    }
}
