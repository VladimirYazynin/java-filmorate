package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FilmControllerTest {

    FilmController controller;
    Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        controller = new FilmController();
    }

    @Test
    void filmValidation_BlankFilmName_ThrowException() {
        Film film = new Film();
        film.setId(1L);
        film.setName("");
        film.setDescription("A mind-bending thriller");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(148);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Название фильма должно быть указано", violations.iterator().next().getMessage());
    }

    @Test
    void filmValidation_DescriptionFilmLength199_ShouldPass() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(199));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        Assertions.assertDoesNotThrow(() -> controller.addFilm(film));
        assertEquals(1, controller.getAllFilms().size());
    }

    @Test
    void filmValidation_DescriptionFilmLength201_ThrowException() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(201));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(120);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Максимальная длина описания — 200 символов", violations.iterator().next().getMessage());
    }

    @Test
    void filmValidation_InvalidReleaseDate_ThrowException() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(199));
        film.setReleaseDate(LocalDate.of(1894, 01, 10));
        film.setDuration(124);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals(
                "Дата релиза не может быть раньше 28 декабря 1895 года",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void filmValidation_InvalidDuration_ThrowException() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(199));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(-99);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
        assertEquals("Продолжительность фильма должна быть положительной",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void filmValidation_ValidObject_ShouldPass() {
        Film film = new Film();
        film.setName("Уроки Фарси");
        film.setDescription("x".repeat(199));
        film.setReleaseDate(LocalDate.of(2020, 05, 14));
        film.setDuration(124);
        Assertions.assertDoesNotThrow(() -> controller.addFilm(film));
        assertEquals(1, controller.getAllFilms().size());
    }

}
