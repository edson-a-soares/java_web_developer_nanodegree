package com.udacity.critter.controller;

import java.util.List;
import java.util.Collection;
import com.udacity.critter.domain.model.pet.Pet;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.application.service.PetService;
import com.udacity.critter.application.representation.PetDTO;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService pets;

    public PetController(PetService service) {
        pets = service;
    }

    @PostMapping
    public PetDTO save(@RequestBody PetDTO representation) {
        Pet entity = new PetDTO
            .EntityBuilder()
            .from(representation)
            .build();

        Pet pet = pets.create(entity);
        return new PetDTO
            .RepresentationBuilder()
            .from(pet)
            .build();
    }

    @GetMapping("/{petId}")
    public PetDTO get(@PathVariable long petId) {
        Pet pet = pets.findByPetId(petId);

        return new PetDTO
            .RepresentationBuilder()
            .from(pet)
            .build();
    }

    @GetMapping
    public List<PetDTO> list(){
        return new PetDTO
            .CollectionBuilder()
            .from(pets.list())
            .build();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Collection<Pet> animals = pets.findByOwnerId(ownerId);
        if (animals.isEmpty())
            return null;

        return new PetDTO
            .CollectionBuilder()
            .from(animals)
            .build();

    }

}
