package com.example.rickyandmortyapp.service.impl;

import com.example.rickyandmortyapp.dto.external.ApiCharacterDto;
import com.example.rickyandmortyapp.dto.external.ApiResponseDto;
import com.example.rickyandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.rickyandmortyapp.model.MovieCharacter;
import com.example.rickyandmortyapp.repository.MovieCharacterRepository;
import com.example.rickyandmortyapp.service.HttpClient;
import com.example.rickyandmortyapp.service.MovieCharacterService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        // todo: fetch all characters from DB where external id is in response
        // todo: if character is present - update
        // todo: else add to DB
        saveDtosToDb(apiResponseDto);

        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        return movieCharacterRepository.getRandomCharacter();
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContainsIgnoreCase(namePart);
    }

    private void saveDtosToDb(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalsDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalsDtos.keySet();

        List<MovieCharacter> existingCharacterDtos = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);

        Set<Long> existingIds = existingCharacterDtos.stream()
                .map(MovieCharacter::getExternalId)
                .collect(Collectors.toSet());

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper.parseDto(externalsDtos.get(i)))
                .collect(Collectors.toList());

        movieCharacterRepository.saveAll(charactersToSave);
    }
}
