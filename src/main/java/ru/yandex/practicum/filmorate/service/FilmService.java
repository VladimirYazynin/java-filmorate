package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.GenreStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.MpaStorage;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.HashSet;
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

    public FilmDto addFilm(FilmDto filmDto) {
        Film film = FilmMapper.mapToFilm(filmDto);
        setGenre(film);
        setMpa(film);
        filmStorage.addFilm(film);
        if (film.getGenres() != null)
            filmStorage.insertGenreForFilm(film.getId(), film.getGenres());
        return FilmMapper.mapToFilmDto(film);
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

    public FilmDto getFilmById(long filmId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Фильм с id: %d не найден.", filmId)
                ));
        searchAndSetGenre(film);
        return FilmMapper.mapToFilmDto(film);
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
        if (film.getMpa() != null) {
            int mpaId = film.getMpa().getId();
            checkMpa(mpaId);
            film.setMpa(mpaStorage.getMpaById(mpaId).get());

        }
        return film;
    }

    private void checkMpa(int mpaId) {
        mpaStorage.getMpaById(mpaId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Mpa с id: %d не найден.", mpaId)
                ));
    }

    public void searchAndSetMpa(Film film) {
        checkFilm(film.getId());
        Mpa mpa = mpaStorage.getMpaByFilmId(film.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Mpa для фильма с id: %d не найден.", film.getId())
                ));
        film.setMpa(mpa);
    }

    public void searchAndSetGenre(Film film) {
        Set<Genre> genres = new HashSet<>();
        genres.addAll(genreStorage.getGenreForFilm(film.getId()));
        film.setGenres(genres);
    }

}
