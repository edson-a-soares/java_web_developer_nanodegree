package com.udacity.critter.domain.model.user;

import java.util.Collection;

public interface CustomerRepositoryInterface {

    public Collection<Customer> list();
    public Customer add(Customer customer);
    public Customer findByPetId(long petId);

}
