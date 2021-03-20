package com.udacity.critter.api;

import java.util.Set;
import java.util.List;
import java.time.DayOfWeek;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.application.representation.EmployeeRequestDTO;
import com.udacity.critter.domain.model.user.EmployeesRepositoryInterface;

@RestController
@RequestMapping("/user")
public class EmployeeController {

    private final EmployeesRepositoryInterface employees;

    public EmployeeController(EmployeesRepositoryInterface repository) {
        this.employees = repository;
    }

    @PostMapping("/employee")
    public Employee save(@RequestBody Employee data) {
        return employees.add(data);
    }

    @PostMapping("/employee/{employeeId}")
    public Employee get(@PathVariable long employeeId) {
        return employees.findById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employees.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employees.add(employee);
    }

    @GetMapping("/employee/availability")
    public List<Employee> findEmployeesForService(@RequestBody EmployeeRequestDTO representation) {
        return employees.findByAvailability(representation.getSkills(), representation.getDate());
    }

}
