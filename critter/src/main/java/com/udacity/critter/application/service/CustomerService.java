package com.udacity.critter.application.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.user.CustomerRepositoryInterface;

@Service
public class CustomerService {

    private final CustomerRepositoryInterface customers;

    public CustomerService(CustomerRepositoryInterface customerRepository) {
        customers = customerRepository;
    }

    public Collection<Customer> list() {
        return customers.list();
    }

    public Customer create(Customer entity) {
        return customers.add(entity);
    }

    public Customer findByPetId(long id) {
        return customers.findByPetId(id);
    }

}
