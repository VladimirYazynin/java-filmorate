package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

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


    // добавить лайк

    // удалить лайк

    // вывести топ 10
}
