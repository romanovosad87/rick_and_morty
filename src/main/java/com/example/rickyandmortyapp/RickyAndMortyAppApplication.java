package com.example.rickyandmortyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RickyAndMortyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickyAndMortyAppApplication.class, args);
    }

}
