package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
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
    public Film addFilm(@RequestBody Film film) {
        log.info("На вход поступили данные для добавления фильма: {}", film);
        film.setId(idCount++);
        validate(film);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film modifiedFilm) {
        log.info("На вход поступили данные для обновления фильма: {}", modifiedFilm);
        validate(modifiedFilm);
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

    private void validate(Film film) {
        String filmName = film.getName();
        if (filmName == null || filmName.isBlank()) {
            log.error("Название фильма должно быть указано");
            throw new ValidationException("Название фильма должно быть указано");
        }
        if (film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
