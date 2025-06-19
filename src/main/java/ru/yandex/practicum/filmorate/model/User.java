package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Long id;
    @NotBlank(message = "Email должен быть указан")
    @Email(message = "Email заполнен некорректно")
    private String email;
    @NotBlank(message = "Логин должен быть указан")
    private String login;
    private String name;
    @NotNull(message = "Дата дня рождения должна быть указана")
    @PastOrPresent(message = "Дата дня рождения не может быть в будущем")
    private LocalDate birthday;

}
