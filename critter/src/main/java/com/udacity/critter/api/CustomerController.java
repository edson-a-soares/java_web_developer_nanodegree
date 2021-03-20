package com.udacity.critter.api;

import java.util.List;
import java.util.Collection;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.application.service.CustomerService;
import com.udacity.critter.application.representation.CustomerDTO;

@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerService customers;

    public CustomerController(CustomerService service) {
        customers = service;
    }

    @PostMapping("/customer")
    public CustomerDTO save(@RequestBody CustomerDTO representation){
        Customer entity = new CustomerDTO
            .EntityBuilder()
            .from(representation)
            .build();

        Customer customer = customers.create(entity);
        return new CustomerDTO
            .RepresentationBuilder()
            .from(customer)
            .build();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> list(){
        Collection<Customer> customers = this.customers.list();

        return new CustomerDTO
            .CollectionBuilder()
            .from(customers)
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
