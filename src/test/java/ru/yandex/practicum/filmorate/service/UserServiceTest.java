package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {

    private final UserService userService;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void init() {
        user1 = createTestUser(1, "user1@mail.ru", "user1", "User 1", LocalDate.of(1990, 1, 1));
        user2 = createTestUser(2, "user2@mail.ru", "user2", "User 2", LocalDate.of(1995, 5, 5));
        user3 = createTestUser(3, "user3@mail.ru", "user3", "User 3", LocalDate.of(2000, 10, 10));
    }

    private User createTestUser(long id, String email, String login, String name, LocalDate birthday) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(birthday);
        return user;
    }

    @Test
    public void createUser_ShouldSetLoginAsNameIfNameIsNull() {
        User userWithoutName = new User();
        userWithoutName.setEmail("noname@mail.ru");
        userWithoutName.setLogin("no_name");
        userWithoutName.setBirthday(LocalDate.of(2000, 1, 1));
        // name не устанавливаем специально

        User createdUser = userService.createUser(userWithoutName);
        assertThat(createdUser.getName()).isEqualTo("no_name");
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        User createdUser = userService.createUser(user1);
        User foundUser = userService.getUserById(createdUser.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("user1@mail.ru");
    }
}