package com.udacity.critter.application.representation;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.modelmapper.ModelMapper;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.pet.PetType;
import com.udacity.critter.domain.model.user.Customer;

public class PetDTO {

    private long id;
    private String name;
    private PetType type;
    private long ownerId;
    private String notes;
    private LocalDate birthDate;

    public PetDTO() {}

    private PetDTO(RepresentationBuilder builder) {
        id          = builder.id;
        type        = builder.type;
        name        = builder.name;
        notes       = builder.notes;
        ownerId     = builder.ownerId;
        birthDate   = builder.birthDate;
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

    /**
     * It builds an entity out of its DTO representation.
     */
    public static class EntityBuilder {

        private PetDTO representation;

        public EntityBuilder() {}

        public EntityBuilder from(PetDTO dto) {
            representation = dto;
            return this;
        }

        public Pet build() {
            ModelMapper modelMapper = new ModelMapper();
            Pet entity = modelMapper.map(representation, Pet.class);

            Customer owner = new Customer();
            owner.setId(representation.ownerId);
            entity.setOwner(owner);

            return entity;
        }

    }

    /**
     * It builds a representation collections out of its Entity collection.
     */
    public static class CollectionBuilder {

        private Collection<Pet> pets;

        public CollectionBuilder from(Collection<Pet> collection) {
            pets = collection;
            return this;
        }

        public List<PetDTO> build() {
            List<PetDTO> asList = new ArrayList<>();
            for (Pet entity : pets)
                asList.add(new PetDTO.RepresentationBuilder().from(entity).build());

            return asList;
        }

    }

    /**
     * It builds a single representation out of its Entity.
     */
    public static class RepresentationBuilder {

        private long id;
        private String name;
        private PetType type;
        private long ownerId;
        private String notes;
        private LocalDate birthDate;

        public RepresentationBuilder from(Pet entity) {
            id          = entity.getId();
            name        = entity.getName();
            type        = entity.getType();
            notes       = entity.getNotes();
            ownerId     = entity.getOwner().getId();
            birthDate   = entity.getBirthDate();
            return this;
        }

        public PetDTO build() {
            return new PetDTO(this);
        }

    }

}
