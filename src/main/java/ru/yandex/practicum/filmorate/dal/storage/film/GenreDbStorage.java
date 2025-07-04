package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GenreDbStorage extends DbStorage implements GenreStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_LIST_QUERY = "SELECT * FROM genres WHERE id IN ";
    private static final String GENRE_FOR_FILM_QUERY =
            "SELECT g.id AS id, g.name AS name FROM films_genres AS fg INNER JOIN genres AS g ON g.id = fg.genre_id WHERE fg.film_id = ?";

    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    @Override
    public Collection<Genre> getAllGenre() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Genre> getGenreById(int genreId) {
        return findOne(FIND_BY_ID_QUERY, genreId);
    }

    @Override
    public List<Genre> getByListId(Set<Integer> genreIds) {
        String idsByString = genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "(", ")"));
        return findMany(FIND_LIST_QUERY + idsByString);
    }

    @Override
    public List<Genre> getGenreForFilm(long id) {
        return findMany(GENRE_FOR_FILM_QUERY, id);
    }

}
