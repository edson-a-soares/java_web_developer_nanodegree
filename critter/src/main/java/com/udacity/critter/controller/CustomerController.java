package com.udacity.critter.controller;

import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.user.CustomerRepositoryInterface;


@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerRepositoryInterface customers;

    public CustomerController(CustomerRepositoryInterface repository) {
        customers = repository;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> save(@RequestBody Customer customer){
        try {
            customer = customers.add(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/customer")
    public Collection<Customer> list(){
        return customers.list();
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<?> getOwnerByPet(@PathVariable long petId){
        try {
            Customer owner = customers.findByPetId(petId);
            return ResponseEntity.ok(owner);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
