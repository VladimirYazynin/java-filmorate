package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;


public class FilmControllerTest {

    FilmController controller;

    @BeforeEach
    void init() {
        controller = new FilmController();
    }

    @Test
    void filmValidation_BlankFilmName_ThrowValidationException() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("x".repeat(100));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.addFilm(film));
        Assertions.assertEquals("Название фильма должно быть указано", exception.getMessage());
    }

    @Test
    void filmValidation_NullFilmName_ThrowValidationException() {
        Film film = new Film();
        film.setDescription("x".repeat(100));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.addFilm(film));
        Assertions.assertEquals("Название фильма должно быть указано", exception.getMessage());
    }

    @Test
    void filmValidation_DescriptionFilmLength199_ShouldPass() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(199));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        Assertions.assertDoesNotThrow(() -> controller.addFilm(film));
        Assertions.assertEquals(1, controller.getAllFilms().size());
    }

    @Test
    void filmValidation_DescriptionFilmLength201_ThrowValidationException() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(201));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.addFilm(film));
        Assertions.assertEquals("Максимальная длина описания — 200 символов", exception.getMessage());
    }

}
