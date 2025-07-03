package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.storage.film.GenreStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public Collection<Genre> getAllGenres() {
        return genreStorage.getAllGenre();
    }

    public Genre getGenreById(int genreId) {
        return genreStorage.getGenreById(genreId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Жанр с id: %d не найден.", genreId)
                ));
    }

}
