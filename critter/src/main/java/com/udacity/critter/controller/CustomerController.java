package com.udacity.critter.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.application.service.CustomerService;
import com.udacity.critter.application.representation.CustomerDTO;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerService customers;
    private final PetRepositoryInterface pets;

    public CustomerController(CustomerService service, PetRepositoryInterface repository) {
        pets = repository;
        customers = service;
    }

    @PostMapping("/customer")
    public CustomerDTO save(@RequestBody CustomerDTO representation){
        Customer asEntity = new CustomerDTO
            .EntityBuilder()
            .with(pets)
            .from(representation)
            .build();

        Customer customer = customers.create(asEntity);
        return new CustomerDTO
            .RepresentationBuilder()
            .from(customer)
            .build();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> list(){
        return new CustomerDTO
            .CollectionBuilder()
            .from(customers.list())
            .build();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customers.findByPetId(petId);

        return new CustomerDTO
            .RepresentationBuilder()
            .from(customer)
            .build();
    }

}
