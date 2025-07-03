package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MpaDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    private String name;
}
