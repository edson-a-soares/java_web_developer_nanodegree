package com.udacity.critter.infrastructure.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;
import com.udacity.critter.domain.model.user.EmployeesRepositoryInterface;

@Component
public class EmployeeRepository implements EmployeesRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Employee add(Employee employee) {
        return manager.merge(employee);
    }

    @Override
    public Employee findById(long id) {
        Employee employee = manager.find(Employee.class, id);
        if (employee == null)
            throw new EntityNotFoundException();

        return employee;
    }

    @Override
    public Collection<Employee> findById(List<Long> ids) {
        return manager.createQuery("SELECT e FROM Employee e WHERE e.id IN :ids")
            .setParameter("ids", ids)
            .getResultList();
    }

    @Override
    public List<Employee> findByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        CriteriaBuilder queryBuilder    = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> query   = queryBuilder.createQuery(Employee.class);
        Root<Employee> employee     = query.from(Employee.class);

        Expression<Collection<EmployeeSkill>> _skills = employee.get("skills");
        // Predicate containsFavoritedProduct = queryBuilder.isMember("favorite-id", skills);

        // Predicate skillsPredicate = queryBuilder.equal(employee.get("skills"), skills);
        // Predicate andPredicate    = queryBuilder.and(skillsPredicate, );
        // query.where(andPredicate);
        // query.where(skillsPredicate);

        // query.select(employee).where(employee.get("skills").in(skills));
        return manager.createQuery(query).getResultList();
    }

}
