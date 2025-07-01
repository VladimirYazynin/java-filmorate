package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.film.GenreStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.MpaStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;

    public Film addFilm(Film film) {
        setGenre(film);
        setMpa(film);
        filmStorage.addFilm(film);
        if (film.getGenres() != null)
            filmStorage.insertGenreForFilm(film.getId(), film.getGenres());
        return film;
    }

    public void updateFilm(Film modifiedFilm) {
        checkFilm(modifiedFilm.getId());
        setGenre(modifiedFilm);
        setMpa(modifiedFilm);
        filmStorage.updateFilm(modifiedFilm);
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

    private void checkFilm(long filmId) {
        filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Фильм с id: %d не найден.", filmId)
                ));
    }

    private Film setGenre(Film film) {
        if (film.getGenres() != null) {
            Set<Integer> genreIds = film.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toSet()
                    );
            List<Genre> foundGenres = genreStorage.getByListId(genreIds);
            Set<Integer> foundIds = foundGenres.stream()
                    .map(Genre::getId)
                    .collect(Collectors.toSet());
            genreIds.removeAll(foundIds);
            if (!genreIds.isEmpty())
                throw new NotFoundException("Не найдены жанры с ID: " + genreIds);
            film.getGenres().clear();
            film.getGenres().addAll(foundGenres);
        }
        return film;
    }

    private Film setMpa(Film film) {
        checkMpa(film.getMpa().getId());
        if (film.getMpa() != null) {
            int mpaId = film.getMpa().getId();
            checkMpa(mpaId);
            film.setMpa(mpaStorage.getMpaById(mpaId).get());
        }
        return film;
    }

    private void checkGenres(Set<Genre> genres) {

        Set<Integer> genreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet()
                );
        Set<Integer> foundIds = genreStorage.getByListId(genreIds).stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
        genreIds.removeAll(foundIds);
        if (!genreIds.isEmpty()) {}
            throw new NotFoundException("Не найдены жанры с ID: " + genreIds);
    }

    private void checkMpa(int mpaId) {
        mpaStorage.getMpaById(mpaId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Mpa с id: %d не найден.", mpaId)
                ));;
    }
}
