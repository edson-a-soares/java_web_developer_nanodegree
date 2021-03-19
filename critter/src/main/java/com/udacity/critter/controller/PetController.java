package com.udacity.critter.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.udacity.critter.domain.model.pet.Pet;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.application.representation.PetDTO;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetRepositoryInterface pets;

    public PetController(PetRepositoryInterface repository) {
        pets = repository;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PetDTO representation) {
        try {
            Pet pet = representation.asEntity();
            pets.add(pet);

            return ResponseEntity.status(HttpStatus.CREATED).body(representation);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{petId}")
    public ResponseEntity<?> get(@PathVariable long petId) {
        Pet pet = pets.findById(petId);
        if (pet == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new PetDTO(pet));
    }

    @GetMapping
    public Collection<PetDTO> list(){
        PetDTO representation = new PetDTO(pets.list());
        return representation.asCollection();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getPetsByOwner(@PathVariable long ownerId) {
        Collection<Pet> animals = pets.findByOwnerId(ownerId);
        if (animals.isEmpty())
            return ResponseEntity.notFound().build();

        PetDTO representation = new PetDTO(animals);
        return ResponseEntity.ok(representation.asCollection());

    }

}
