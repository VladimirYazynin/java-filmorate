package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User modifiedUser);

    User deleteUser(Long userId);

    User getUserById(Long userId);

    Collection<User> getAllUsers();

}
