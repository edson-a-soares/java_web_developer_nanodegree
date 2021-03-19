package com.udacity.critter.infrastructure.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import com.udacity.critter.domain.model.pet.Pet;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.user.CustomerRepositoryInterface;

@Component
public class CustomerRepository implements CustomerRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Customer> list() {
        return manager.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    @Transactional
    public Customer add(Customer customer) {
        return manager.merge(customer);
    }

    @Override
    public Customer findByPetId(long petId) {
        Pet pet = manager.find(Pet.class, petId);
        if (pet == null)
            throw new EntityNotFoundException();

        Customer owner = manager.find(Customer.class, pet.getOwner().getId());
        if (owner == null)
            throw new EntityNotFoundException();

        return owner;
    }

}
