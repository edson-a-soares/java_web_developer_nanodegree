package com.udacity.critter.domain.model.pet;

import java.util.List;
import java.util.Collection;

public interface PetRepositoryInterface {

    public Pet add(Pet pet);
    public Pet findById(long id);
    public Collection<Pet> list();
    public Collection<Pet> findById(List<Long> ids);
    public Collection<Pet> findByOwnerId(long ownerId);

}
