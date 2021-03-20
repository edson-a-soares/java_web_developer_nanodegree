package com.udacity.critter.domain.model.user;

import java.util.Set;
import java.util.List;
import java.time.LocalDate;
import java.util.Collection;

public interface EmployeesRepositoryInterface {

    public Employee add(Employee employee);
    public Employee findById(long id);
    public Collection<Employee> findById(List<Long> ids);
    public Collection<Employee> findByAvailability(Set<EmployeeSkill> skills, LocalDate date);

}
