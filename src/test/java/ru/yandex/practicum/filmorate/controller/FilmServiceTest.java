package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmServiceTest {

    private final FilmService filmService;

    Film film;

    @BeforeEach
    public void addTestFilm() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        Genre genre = new Genre();
        genre.setId(6);
        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        film = new Film();
        film.setName("name");
        film.setDescription("v".repeat(15));
        film.setDuration(90);
        film.setReleaseDate(LocalDate.of(2005,1,1));
        film.setMpa(mpa);
        film.setGenres(genres);
    }

    @Test
    public void addFilmTest() {
        FilmDto filmCreate = filmService.addFilm(film);
        assertThat(filmCreate)
                .hasFieldOrPropertyWithValue("mpa.name", "G");
    }

    @Test
    public void getFilmByIdTest() {
        FilmDto filmCreate = filmService.addFilm(film);
        FilmDto filmCreateDB = filmService.getFilmById(film.getId());
        assertThat(filmCreateDB)
                .hasFieldOrPropertyWithValue("id", film.getId())
                .hasFieldOrPropertyWithValue("name", "name");
        assertThat(filmCreate.getGenres())
                .extracting(Genre::getName)
                .contains("Боевик");
    }

}
