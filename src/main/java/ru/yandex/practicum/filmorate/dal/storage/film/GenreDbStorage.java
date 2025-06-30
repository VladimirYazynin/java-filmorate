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

    private final static String FIND_ALL_QUERY = "SELECT * FROM genres";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private final static String FIND_LIST_QUERY = "SELECT * FROM genres WHERE id IN ";

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
        System.out.println(idsByString);
        return findMany(FIND_LIST_QUERY + idsByString);
    }

}
