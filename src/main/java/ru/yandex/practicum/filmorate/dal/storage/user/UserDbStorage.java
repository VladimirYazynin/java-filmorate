package ru.yandex.practicum.filmorate.dal.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.storage.DbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserDbStorage extends DbStorage implements UserStorage {

    private final static String FIND_ALL_QUERY = "SELECT * FROM users";
    private final static String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private final static String INSERT_QUERY = "INSERT INTO users(email, login, name, birthday) VALUES (?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
    private final static String DELETE_QUERY = "DELETE FROM users WHERE id = ?";


    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }

    @Override
    public User addUser(User user) {
        Long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
                );
        user.setId(id);
        return user;
    }

    @Override
    public void updateUser(User modifiedUser) {
        update(
                UPDATE_QUERY,
                modifiedUser.getEmail(),
                modifiedUser.getLogin(),
                modifiedUser.getName(),
                modifiedUser.getBirthday(),
                modifiedUser.getId()
        );
    }

    @Override
    public void deleteUser(Long userId) {
        update(DELETE_QUERY, userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return findOne(FIND_BY_ID_QUERY, userId);
    }

    @Override
    public Collection<User> getAllUsers() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public void addFriend(long userId, long friendId) {

    }

    @Override
    public void deleteFriend(long userId, long friendId) {

    }

    @Override
    public Collection<User> getUserFriends(long userId) {
        return null;
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        return null;
    }
}
