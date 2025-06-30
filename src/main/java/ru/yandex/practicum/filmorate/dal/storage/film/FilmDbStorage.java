package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmDbStorage extends DbStorage implements FilmStorage {

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM films";
    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM films WHERE id = ?";
    private static final String INSERT_QUERY =
            "INSERT INTO films(name, description, releaseDate, duration, rating_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY =
            "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ?, rating_id = ? WHERE id = ?";
    private static final String DELETE_QUERY =
            "DELETE FROM films WHERE id = ?";
    private static final String POPULAR_FILM_QUERY =
            "SELECT f.id, f.name, f.description, f.releaseDate, f.duration, COUNT(l.id) AS rate FROM films AS f LEFT JOIN likes AS l ON l.film_id = f.id GROUP BY f.id, f.name, f.description, f.releaseDate, f.duration ORDER BY COUNT(l.id) DESC LIMIT ?";

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    @Override
    public Film addFilm(Film film) {
        Long id = insert(INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId()
                );
        film.setId(id);
        return film;
    }

    @Override
    public void updateFilm(Film modifiedFilm) {
        update(UPDATE_QUERY,
                modifiedFilm.getName(),
                modifiedFilm.getDescription(),
                modifiedFilm.getReleaseDate(),
                modifiedFilm.getDuration(),
                modifiedFilm.getMpa().getId(),
                modifiedFilm.getId()
        );
    }

    @Override
    public void deleteFilm(Long filmId) {
        delete(DELETE_QUERY, filmId);
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public void addLike(long userId, long filmId) {

    }

    @Override
    public void deleteLike(long userId, long filmId) {

    }

    @Override
    public Collection<Film> getPopularFilms(long filmCount) {
        return findMany(POPULAR_FILM_QUERY, filmCount);
    }
}
