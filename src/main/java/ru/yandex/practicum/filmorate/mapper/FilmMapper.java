package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper {

    public static FilmDto mapToFilmDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setDuration(film.getDuration());
        filmDto.setMpa(film.getMpa());
        if (film.getGenres() == null) {
            film.setGenres(new HashSet<>());
        }

        filmDto.setGenres(
                film.getGenres().stream()
                        .sorted(Comparator.comparingInt(Genre::getId))
                        .collect(Collectors.toList())
        );

        return filmDto;
    }

    public static Film mapToFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        film.setReleaseDate(filmDto.getReleaseDate());
        film.setDuration(filmDto.getDuration());
        film.setMpa(filmDto.getMpa());

        if (filmDto.getGenres() != null) {
            film.setGenres(new HashSet<>(filmDto.getGenres()));
        } else {
            film.setGenres(new HashSet<>());
        }

        return film;
    }

}
