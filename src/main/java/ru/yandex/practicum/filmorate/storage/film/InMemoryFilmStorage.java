package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long generatorId = 1L;

    public long generateId() {
        return generatorId++;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> updateFilm(Film modifiedFilm) {
        if (!films.containsKey(modifiedFilm.getId()))
            return Optional.empty();
        films.put(modifiedFilm.getId(), modifiedFilm);
        return Optional.of(modifiedFilm);
    }

    @Override
    public Film deleteFilm(Long filmId) {
        return films.remove(filmId);
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        return Optional.of(films.get(filmId));
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }
}
