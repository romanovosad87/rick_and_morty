package com.example.rickyandmortyapp.controller;

import com.example.rickyandmortyapp.dto.CharacterResponseDto;
import com.example.rickyandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickyandmortyapp.model.MovieCharacter;
import com.example.rickyandmortyapp.service.MovieCharacterService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movie-character")
public class MovieCharacterController {

    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper movieCharacterMapper;

    @GetMapping("/random")
    @Operation(summary = "get random character")
    public CharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @Operation(summary = "find character by name part")
    public List<CharacterResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart)
                .stream()
                .map(movieCharacterMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/download")
    public String download() {
        movieCharacterService.syncExternalCharacters();
        return "Done";
    }
}
