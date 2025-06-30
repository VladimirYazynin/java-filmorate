package ru.yandex.practicum.filmorate.dal.storage.film;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreStorage {

    Collection<Genre> getAllGenre();
    Optional<Genre> getGenreById(int genreId);
    List<Genre> getByListId(Set<Integer> genreIds);

}
