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
}
