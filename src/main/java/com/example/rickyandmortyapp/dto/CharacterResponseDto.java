package com.example.rickyandmortyapp.dto;

import com.example.rickyandmortyapp.model.Gender;
import com.example.rickyandmortyapp.model.Status;
import lombok.Data;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
