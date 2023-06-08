package com.example.rickyandmortyapp.dto.mapper;

import com.example.rickyandmortyapp.dto.CharacterResponseDto;
import com.example.rickyandmortyapp.dto.external.ApiCharacterDto;
import com.example.rickyandmortyapp.model.Gender;
import com.example.rickyandmortyapp.model.MovieCharacter;
import com.example.rickyandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {

    public MovieCharacter parseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());
        return movieCharacter;
    }

    public CharacterResponseDto toDto(MovieCharacter movieCharacter) {
        CharacterResponseDto dto = new CharacterResponseDto();
        dto.setId(movieCharacter.getId());
        dto.setName(movieCharacter.getName());
        dto.setExternalId(movieCharacter.getExternalId());
        dto.setStatus(movieCharacter.getStatus());
        dto.setGender(movieCharacter.getGender());
        return dto;
    }
}
