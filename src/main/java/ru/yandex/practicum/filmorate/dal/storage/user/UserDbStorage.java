package ru.yandex.practicum.filmorate.dal.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserDbStorage implements UserStorage {
    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public void updateUser(User modifiedUser) {

    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
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
