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

    //добавить друга

    //удалить друга

    //показать общих друзей
}
