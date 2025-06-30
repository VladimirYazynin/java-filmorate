package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Long id;
    @NotBlank(message = "Название фильма должно быть указано")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @ReleaseDate
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма должна быть положительной")
    private Integer duration;
    @JsonProperty(value = "mpa")
    private int rating;
    private final Set<Long> likes = new HashSet<>();
    private final Set<Genre> genres = new HashSet<>();

}
