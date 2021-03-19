package com.udacity.critter.controller;

import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.udacity.critter.domain.model.pet.Pet;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetRepositoryInterface pets;

    public PetController(PetRepositoryInterface repository) {
        pets = repository;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Pet pet) {
        try {
            pet = pets.add(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(pet);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> get(@PathVariable long petId) {
        Pet pet = pets.findById(petId);
        if (pet == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(pet);
    }

    @GetMapping
    public Collection<Pet> list(){
        return pets.list();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getPetsByOwner(@PathVariable long ownerId) {
        Collection<Pet> animals = pets.findByOwnerId(ownerId);
        if (animals.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(animals);

    }

}
