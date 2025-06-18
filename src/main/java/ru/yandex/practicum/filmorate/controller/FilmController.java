package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Long, Film> films = new HashMap<>();
    private long idCount = 1L;

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("На вход поступили данные для добавления фильма: {}", film);
        film.setId(idCount++);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film modifiedFilm) {
        log.info("На вход поступили данные для обновления фильма: {}", modifiedFilm);
        Film oldFilm = films.get(modifiedFilm.getId());
        oldFilm.setName(modifiedFilm.getName());
        oldFilm.setDescription(modifiedFilm.getDescription());
        oldFilm.setReleaseDate(modifiedFilm.getReleaseDate());
        oldFilm.setDuration(modifiedFilm.getDuration());
        log.info("Обновлён фильм: {}", oldFilm);
        return modifiedFilm;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

}
