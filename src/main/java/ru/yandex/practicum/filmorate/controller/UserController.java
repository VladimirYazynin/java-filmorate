package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        log.info("На вход поступили данные для добавления пользователя: {}", user);
        if (user.getName() == null)
            user.setName(user.getLogin());
        user.setId(idCount++);
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User modifiedUser) {
        log.info("На вход поступили данные для обновления пользователя: {}", modifiedUser);
        if (modifiedUser.getName() == null)
            modifiedUser.setName(modifiedUser.getLogin());
        User oldUser = users.get(modifiedUser.getId());
        oldUser.setName(modifiedUser.getName());
        oldUser.setLogin(modifiedUser.getLogin());
        oldUser.setEmail(modifiedUser.getEmail());
        oldUser.setBirthday(modifiedUser.getBirthday());
        log.info("Данные по пользователю обновлены: {}", oldUser);
        return modifiedUser;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

}
