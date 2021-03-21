package com.udacity.critter.domain.model.pet;

import lombok.Getter;

@Getter
public enum PetType {

    CAT ("CAT"),
    DOG ("DOG"),
    BIRD ("BIRD"),
    FISH ("FISH"),
    SNAKE ("SNAKE"),
    LIZARD ("LIZARD"),
    OTHER ("OTHER");

    private final String code;

    private PetType(String code) {
        this.code = code;
    }

}
