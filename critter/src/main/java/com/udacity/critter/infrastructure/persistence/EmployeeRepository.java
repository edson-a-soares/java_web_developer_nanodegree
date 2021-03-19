package com.udacity.critter.infrastructure.persistence;

import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import com.udacity.critter.domain.model.user.Employee;
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
}
