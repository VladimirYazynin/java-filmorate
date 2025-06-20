package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long generatorId = 0L;

    public long generateId() {
        return ++generatorId;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film modifiedFilm) {
        films.put(modifiedFilm.getId(), modifiedFilm);
        return modifiedFilm;
    }

    @Override
    public Film deleteFilm(Long filmId) {
        return films.remove(filmId);
    }

    @Override
    public Film getFilmById(Long filmId) {
        return films.get(filmId);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}
