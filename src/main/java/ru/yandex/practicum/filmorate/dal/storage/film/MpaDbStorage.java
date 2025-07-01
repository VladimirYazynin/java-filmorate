package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DbStorage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

@Repository
public class MpaDbStorage extends DbStorage implements MpaStorage{

    private final static String FIND_ALL_QUERY = "SELECT * FROM rating";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM rating WHERE id = ?";
    private static final String FIND_MPA_BY_FILM_ID_QUERY =
            "SELECT r.id, r.name, r.description FROM films AS f INNER JOIN rating AS r ON f.rating_id=r.id WHERE f.id = ?";


    public MpaDbStorage(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
        super(jdbc, mapper, Mpa.class);
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Mpa> getMpaById(int mpaId) {
        return findOne(FIND_BY_ID_QUERY, mpaId);
    }

    @Override
    public Optional<Mpa> getMpaByFilmId(long filmId) {
        return findOne(FIND_MPA_BY_FILM_ID_QUERY, filmId);
    }

}
