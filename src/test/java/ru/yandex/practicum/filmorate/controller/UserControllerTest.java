package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {

    UserController controller;

    @BeforeEach
    void init() {
        controller = new UserController();
    }

    @Test
    void userValid_BlankEmail_ThrowValidationException() {
        User user = new User();
        user.setEmail(" ");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Email должен быть указан", exception.getMessage());
    }

    @Test
    void userValid_NullEmail_ThrowValidationException() {
        User user = new User();
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Email должен быть указан", exception.getMessage());
    }

    @Test
    void userValid_InvalidEmail_ThrowValidationException() {
        User user = new User();
        user.setEmail("alex.ivanonAya.ru");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Email заполнен некорректно", exception.getMessage());
    }

    @Test
    void userValid_InvalidLogin_ThrowValidationException() {
        User user = new User();
        user.setEmail("alex.ivanon@ya.ru");
        user.setLogin("");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(1995, 8, 21));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Логин должен быть указан", exception.getMessage());
        user.setLogin(null);
        ValidationException ex = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Логин должен быть указан", ex.getMessage());
        user.setLogin("alex. ivanov");
        ValidationException e = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Логин не должен иметь пробелы", e.getMessage());
    }

    @Test
    void userValid_BirtdayInFuture_ThrowValidationException() {
        User user = new User();
        user.setEmail("alex.ivanon@ya.ru");
        user.setLogin("alex.ivanov");
        user.setName("Alexey");
        user.setBirthday(LocalDate.of(2255, 8, 21));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> controller.createUser(user));
        Assertions.assertEquals("Дата дня рождения не может быть в будущем", exception.getMessage());
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
