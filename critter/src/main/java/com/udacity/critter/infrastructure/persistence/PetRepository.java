package com.udacity.critter.infrastructure.persistence;

import java.util.List;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Component;
import com.udacity.critter.domain.model.pet.Pet;
import javax.persistence.criteria.CriteriaBuilder;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;

@Component
public class PetRepository implements PetRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Pet add(Pet pet) {
        return manager.merge(pet);
    }

    @Override
    public Pet findById(long id) {
        return manager.find(Pet.class, id);
    }

    @Override
    public List<Pet> list() {
        return manager.createQuery("from Pet", Pet.class).getResultList();
    }

    @Override
    public Collection<Pet> findById(List<Long> ids) {
        return manager.createQuery("SELECT p FROM Pet p WHERE p.id IN :ids")
            .setParameter("ids", ids)
            .getResultList();
    }

    @Override
    public Collection<Pet> findByOwnerId(long ownerId) {
        CriteriaBuilder queryBuilder    = manager.getCriteriaBuilder();
        CriteriaQuery<Pet> query 	    = queryBuilder.createQuery(Pet.class);
        Root<Pet> pet			        = query.from(Pet.class);

        Predicate equal = queryBuilder.equal(pet.get("owner").get("id"), ownerId);
        query.select(pet).where(equal);

        return manager.createQuery(query).getResultList();
    }

}
