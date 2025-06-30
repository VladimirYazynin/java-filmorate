package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dal.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

    @Qualifier("userDbStorage")
    private final UserStorage userStorage;

    public User createUser(User user) {
        if (user.getName() == null)
            user.setName(user.getLogin());
        return userStorage.addUser(user);
    }

    public void updateUser(User modifiedUser) {
        if (modifiedUser.getName() == null)
            modifiedUser.setName(modifiedUser.getLogin());
        checkUserInStorage(modifiedUser.getId());
        userStorage.updateUser(modifiedUser);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(long userId) {
        return userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь с id: %d не найден.", userId)
                ));
    }

    public void addFriend(long userId, long friendId) {
        checkUserInStorage(userId);
        checkUserInStorage(friendId);
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        checkUserInStorage(userId);
        checkUserInStorage(friendId);
        userStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getUserFriends(long userId) {
        checkUserInStorage(userId);
        return userStorage.getUserFriends(userId);
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        checkUserInStorage(userId);
        checkUserInStorage(otherId);
        return userStorage.getCommonFriends(userId, otherId);
    }

    private void checkUserInStorage(long userId) {
        userStorage.getUserById(userId)
            .orElseThrow(() -> new NotFoundException(
                String.format("Пользователь с id: %d не найден.", userId)
        ));
    }
}
