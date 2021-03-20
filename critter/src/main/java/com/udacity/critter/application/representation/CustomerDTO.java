package com.udacity.critter.application.representation;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import org.modelmapper.ModelMapper;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

public class CustomerDTO {

    private long id;
    private String name;
    private String notes;
    private String phoneNumber;
    private List<Long> petIds;

    public CustomerDTO() {}

    private CustomerDTO(RepresentationBuilder builder) {
        id          = builder.id;
        name        = builder.name;
        notes       = builder.notes;
        petIds      = builder.petIds;
        phoneNumber = builder.phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    /**
     * It builds an entity out of its DTO representation.
     */
    public static class EntityBuilder {

        private CustomerDTO representation;
        private PetRepositoryInterface pets;

        public EntityBuilder() {}

        public EntityBuilder with (PetRepositoryInterface repository) {
            pets = repository;
            return this;
        }

        public EntityBuilder from(CustomerDTO dto) {
            representation = dto;
            return this;
        }

        public Customer build() {
            ModelMapper modelMapper = new ModelMapper();
            Customer entity = modelMapper.map(representation, Customer.class);

            if (pets != null && !representation.getPetIds().isEmpty())
                entity.setPets(new ArrayList<>(pets.findById(representation.getPetIds())));
            else
                entity.setPets(new ArrayList<>());

            return entity;
        }

    }

    /**
     * It builds a representation collections out of its Entity collection.
     */
    public static class CollectionBuilder {

        private Collection<Customer> customers;

        public CollectionBuilder from(Collection<Customer> collection) {
            customers = collection;
            return this;
        }

        public List<CustomerDTO> build() {
            List<CustomerDTO> asList = new ArrayList<>();
            for (Customer entity : customers)
                asList.add(new RepresentationBuilder().from(entity).build());

            return asList;
        }

    }

    /**
     * It builds a single representation out of its Entity.
     */
    public static class RepresentationBuilder {

        private long id;
        private String name;
        private String notes;
        private String phoneNumber;
        private List<Long> petIds;

        public RepresentationBuilder from(Customer entity) {
            id          = entity.getId();
            name        = entity.getName();
            notes       = entity.getNotes();
            phoneNumber = entity.getPhoneNumber();

            petIds = new ArrayList<Long>();
            for (Pet pet : entity.getPets())
                petIds.add(pet.getId());

            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }

    }

}
