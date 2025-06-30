package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
@Qualifier("filmDBStorage")
public class FilmDbStorage extends DbStorage implements FilmStorage {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_BY_UD_QUERY = "";
    private static final String INSERT_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_QUERY = "";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public void updateFilm(Film modifiedFilm) {

    }

    @Override
    public void deleteFilm(Long filmId) {

    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {

        return Optional.empty();
    }

    @Override
    public Collection<Film> getAllFilms() {
        return null;
    }

    @Override
    public void addLike(long userId, long filmId) {

    }

    @Override
    public void deleteLike(long userId, long filmId) {

    }

    @Override
    public Collection<Film> getPopularFilms(long filmCount) {
        return null;
    }
}
