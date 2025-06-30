package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void updateFilm(Film modifiedFilm) {
        if (films.containsKey(modifiedFilm.getId()))
            films.put(modifiedFilm.getId(), modifiedFilm);
    }

    @Override
    public void deleteFilm(Long filmId) {
        films.remove(filmId);
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public void addLike(long userId, long filmId) {
        films.get(filmId).getLikes().add(userId);
    }

    @Override
    public void deleteLike(long userId, long filmId) {
        films.get(filmId).getLikes().remove(filmId);
    }

    @Override
    public Collection<Film> getPopularFilms(long filmCount) {
        return films.values().stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getLikes().size()).reversed())
                .limit(filmCount)
                .collect(Collectors.toList());
    }

}
