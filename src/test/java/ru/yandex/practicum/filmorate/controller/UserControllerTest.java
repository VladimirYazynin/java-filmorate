package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserController controller;
    Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userValid_BlankEmail_ThrowException() {
        User user = new User();
        user.setEmail(" ");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size());
    }

    @Test
    void userValid_InvalidLogin_ThrowValidationException() {
        User user = new User();
        user.setEmail("alex.ivanon@ya.ru");
        user.setLogin("");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Логин должен быть указан", violations.iterator().next().getMessage());
    }

    @Test
    void userValid_BirtdayInFuture_ThrowValidationException() {
        User user = new User();
        user.setEmail("alex.ivanon@ya.ru");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(2255, 8, 21));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("Дата дня рождения не может быть в будущем", violations.iterator().next().getMessage());
    }

    @Test
    void userValid_ValidObject_ShouldPass() {
        User user = new User();
        user.setEmail("alex.ivanon@ya.ru");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        Assertions.assertDoesNotThrow(() -> controller.createUser(user));
        Assertions.assertEquals(1, controller.getAllUsers().size());
    }
}
