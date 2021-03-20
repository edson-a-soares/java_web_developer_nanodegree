package com.udacity.critter.application.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@Service
public class PetService {

    private final PetRepositoryInterface pets;

    public PetService(PetRepositoryInterface repository) {
        pets = repository;
    }

    public Collection<Pet> list() {
        return pets.list();
    }

    public Pet create(Pet entity) {
        return pets.add(entity);
    }

    public Pet findByPetId(long id) {
        return pets.findById(id);
    }

    public Collection<Pet> findByOwnerId(long id) {
        return pets.findByOwnerId(id);
    }

}
