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
    private final static String ADD_FRIEND_QUERY = "INSERT INTO friends(user_id, friend_id) VALUES (?, ?)";
    private final static String DELETE_FRIEND_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private final static String FIND_USER_FRIENDS_QUERY = "SELECT u.id, u.email, u.login, u.name, u.birthday FROM friends AS f INNER JOIN users AS u ON f.friend_id = u.id WHERE f.user_id = ?";
    private final static String FIND_USERS_COMMON_FRIENDS_QUERY = "SELECT u.id, u.email, u.login, u.name, u.birthday " +
                                                                    " FROM friends f" +
                                                                    " JOIN users u ON f.friend_id = u.id" +
                                                                    " WHERE f.user_id = ?" +
                                                                    " INTERSECT" +
                                                                    " SELECT u.id, u.name, u.email, u.login, u.birthday" +
                                                                    " FROM friends f" +
                                                                    " JOIN users u ON f.friend_id = u.id" +
                                                                    " WHERE f.user_id = ?";


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
        insert(
                ADD_FRIEND_QUERY,
                userId,
                friendId
        );
    }

    @Override
    public void deleteFriend(long userId, long friendId) {
        update(
                DELETE_FRIEND_QUERY,
                userId,
                friendId
        );
    }

    @Override
    public Collection<User> getUserFriends(long userId) {
        return findMany(FIND_USER_FRIENDS_QUERY, userId);
    }

    @Override
    public Collection<User> getCommonFriends(long userId, long otherId) {
        return findMany(
                FIND_USERS_COMMON_FRIENDS_QUERY,
                userId,
                otherId
        );
    }
}
