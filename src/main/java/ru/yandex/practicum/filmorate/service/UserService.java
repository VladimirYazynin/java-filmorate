package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User createUser(User user) {
        if (user.getName() == null)
            user.setName(user.getLogin());
        return userStorage.addUser(user);
    }

    public User updateUser(User modifiedUser) {
        if (modifiedUser.getName() == null)
            modifiedUser.setName(modifiedUser.getLogin());
        return userStorage.updateUser(modifiedUser)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь с id: %d не найден.", modifiedUser.getId())
                ));
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
        checkUserInStorage(userId, "Пользователь с id: %d не найден.");
        checkUserInStorage(friendId, "Друг с id: %d не найден.");
        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(long userId, long friendId) {
        checkUserInStorage(userId, "Пользователь с id: %d не найден.");
        checkUserInStorage(friendId, "Друг с id: %d не найден.");
        userStorage.deleteFriend(userId, friendId);
    }

    public Collection<User> getUserFriends(long userId) {
        checkUserInStorage(userId, "Пользователь с id: %d не найден.");
        return userStorage.getUserFriends(userId);
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        checkUserInStorage(userId, "Пользователь с id: %d не найден.");
        checkUserInStorage(userId, "Другой пользователь с id: %d не найден.");
        return userStorage.getCommonFriends(userId, otherId);
    }

    private void checkUserInStorage(long userId, String message) {
        userStorage.getUserById(userId)
            .orElseThrow(() -> new NotFoundException(
                String.format(message, userId)
        ));
    }
}
