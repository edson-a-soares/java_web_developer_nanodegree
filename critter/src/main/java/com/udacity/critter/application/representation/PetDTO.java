package com.udacity.critter.application.representation;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.pet.PetType;
import com.udacity.critter.domain.model.user.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "pets" })
public class PetDTO {

    private long id;
    private String name;
    private PetType type;
    private long ownerId;
    private String notes;
    private LocalDate birthDate;

    Collection<Pet> pets;

    public PetDTO(Pet entity) {
        id = entity.getId();
        type = entity.getType();
        name = entity.getName();
        notes = entity.getNotes();
        birthDate = entity.getBirthDate();
        ownerId = entity.getOwner().getId();
    }

    public PetDTO(Collection<Pet> collection) {
        pets = collection;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pet asEntity() {
        Pet pet = new Pet();

        pet.setId(id);
        pet.setType(type);
        pet.setName(name);
        pet.setNotes(notes);
        pet.setBirthDate(birthDate);

        Customer owner = new Customer();
        owner.setId(ownerId);
        pet.setOwner(owner);

        return pet;
    }

    public Collection<PetDTO> asCollection() {
        List<PetDTO>  asList = new ArrayList<PetDTO>();

        for (Pet entity : pets)
            asList.add(new PetDTO(entity));

        return asList;
    }

}
