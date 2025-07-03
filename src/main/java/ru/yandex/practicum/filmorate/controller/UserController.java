package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        log.info("На вход поступили данные для добавления пользователя: {}", user);
        userService.createUser(user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User modifiedUser) {
        log.info("На вход поступили данные для обновления пользователя: {}", modifiedUser);
        userService.updateUser(modifiedUser);
        log.info("Данные по пользователю c id: {} обновлены.", modifiedUser.getId());
        return modifiedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFriend(@PathVariable(value = "id") long userId, @PathVariable long friendId) {
        log.info("Поступил запрос добавления в друзья пользователю {} от пользователя {}", userId, friendId);
        userService.addFriend(userId, friendId);
        log.info("Пользователь {} добавлен в друзья к пользователю {}", friendId, userId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriend(@PathVariable(value = "id") long userId, @PathVariable long friendId) {
        log.info("Поступил запрос на удаление из друзей пользователя {} от пользователя {}", friendId, userId);
        userService.deleteFriend(userId, friendId);
        log.info("Пользователь с id: {} успешно удалён из списка друзей", friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable(value = "id") long userId) {
        return userService.getUserFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable(value = "id") long userId, @PathVariable long otherId) {
        return userService.getCommonFriends(userId, otherId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
