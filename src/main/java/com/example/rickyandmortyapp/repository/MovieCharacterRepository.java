package com.example.rickyandmortyapp.repository;

import com.example.rickyandmortyapp.model.MovieCharacter;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    @Query("from MovieCharacter ORDER BY random() limit 1")
    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContainsIgnoreCase(String namePart);
}
