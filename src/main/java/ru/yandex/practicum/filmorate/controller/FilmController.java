package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDto addFilm(@RequestBody @Valid FilmDto filmDto) {
        log.info("На вход поступили данные для добавления фильма: {}", filmDto);
        FilmDto resultFilmDto = filmService.addFilm(filmDto);
        log.info("Добавлен фильм: {}", filmDto);
        return resultFilmDto;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film modifiedFilm) {
        log.info("На вход поступили данные для обновления фильма: {}", modifiedFilm);
        filmService.updateFilm(modifiedFilm);
        log.info("Обновлён фильм c id: {}", modifiedFilm.getId());
        return modifiedFilm;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable(value = "id") long filmId) {
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        filmService.addLike(userId, filmId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        filmService.deleteLike(userId, filmId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") long count) {
        log.info("Пришёл запрос на получение {} самых популярных фильмов", count);
        return filmService.getPopularFilms(count);
    }

}
