package com.udacity.critter.application.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@Service
public class PetService {

    private final CustomerService customers;
    private final PetRepositoryInterface pets;

    public PetService(PetRepositoryInterface repository, CustomerService service) {
        pets = repository;
        customers = service;
    }

    public Collection<Pet> list() {
        return pets.list();
    }

    public Pet create(Pet entity) {
        Pet pet = pets.add(entity);
        Customer customer = pet.getOwner();
        if (customer != null){
            customer.getPets().add(pet);
            customers.create(customer);
        }

        return pet;
    }

    public Pet findByPetId(long id) {
        return pets.findById(id);
    }

    public Collection<Pet> findByOwnerId(long id) {
        return pets.findByOwnerId(id);
    }

}
