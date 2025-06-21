package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private final Map<Long, Set<Long>> likes = new HashMap<>();
    private long generatorId = 1L;

    public long generateId() {
        return generatorId++;
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
        likes.put(film.getId(), new HashSet<>());
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
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public void addLike(long userId, long filmId) {
        likes.get(filmId).add(userId);
    }

    @Override
    public void deleteLike(long userId, long filmId) {
        likes.get(filmId).remove(userId);
    }

    @Override
    public Collection<Film> getPopularFilms(long filmCount) {
        return films.values().stream()
                .sorted((f1, f2) -> {
                    int likes1 = likes.getOrDefault(f1.getId(), Collections.emptySet()).size();
                    int likes2 = likes.getOrDefault(f2.getId(), Collections.emptySet()).size();
                    return Integer.compare(likes2, likes1);
                })
                .limit(filmCount)
                .collect(Collectors.toList());
    }

}
