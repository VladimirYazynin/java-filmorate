package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private long idCount = 1L;

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(idCount++);
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User modifiedUser) {
        validate(modifiedUser);
        User oldUser = users.get(modifiedUser.getId());
        oldUser.setName(modifiedUser.getName());
        oldUser.setLogin(modifiedUser.getLogin());
        oldUser.setEmail(modifiedUser.getEmail());
        oldUser.setBirthday(modifiedUser.getBirthday());
        return modifiedUser;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    private void validate(User user) {
        String userEmail = user.getEmail();
        String userLogin = user.getLogin();
        if (userEmail == null || userEmail.isBlank())
            throw new ValidationException("Email должен быть указан");
        if (!userEmail.contains("@"))
            throw new ValidationException("Email заполнен некорректно");
        if (userLogin == null || userLogin.isBlank())
            throw new ValidationException("Логин должен быть указан");
        if (userLogin.contains(" "))
            throw new ValidationException("Логин не должен иметь пробелы");
        if (user.getName() == null || user.getName().isBlank())
            user.setName(userLogin);
        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Дата дня рождения не может быть в будущем");
    }

}
