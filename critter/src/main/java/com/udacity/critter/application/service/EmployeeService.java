package com.udacity.critter.application.service;

import java.util.Set;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.stereotype.Service;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;
import com.udacity.critter.domain.model.user.EmployeesRepositoryInterface;

@Service
public class EmployeeService {

    private final EmployeesRepositoryInterface employees;

    public EmployeeService(EmployeesRepositoryInterface repository) {
        employees = repository;
    }

    public Employee create(Employee entity) {
        return employees.add(entity);
    }

    public Employee findById(long id) {
        return employees.findById(id);
    }

    public Collection<Employee> findByAvailability(Set<EmployeeSkill> skills, LocalDate date) {
        return employees.findByAvailability(skills, date);
    }

}
