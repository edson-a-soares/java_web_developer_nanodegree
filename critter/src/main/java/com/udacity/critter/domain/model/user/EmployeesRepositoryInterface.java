package com.udacity.critter.domain.model.user;

public interface EmployeesRepositoryInterface {

    public Employee add(Employee employee);
    public Employee findById(long id);

}
