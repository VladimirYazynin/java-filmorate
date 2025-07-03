package ru.yandex.practicum.filmorate.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Mpa;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MpaMapper {
    public static MpaDto mapToMpaDto(Mpa mpa) {
        MpaDto mpaDto = new MpaDto();
        mpaDto.setId(mpa.getId());
        mpaDto.setName(mpa.getName());

        return mpaDto;
    }
}
