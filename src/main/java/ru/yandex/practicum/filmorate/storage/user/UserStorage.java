package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    User addUser(User user);

    void updateUser(User modifiedUser);

    void deleteUser(Long userId);

    Optional<User> getUserById(Long userId);

    Collection<User> getAllUsers();

    void addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);

    Collection<User> getUserFriends(long userId);

    Collection<User> getCommonFriends(long userId, long otherId);

}
