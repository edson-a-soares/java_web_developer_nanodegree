package com.udacity.critter.controller;

import java.util.Set;
import java.util.List;
import java.time.DayOfWeek;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import com.udacity.critter.domain.model.user.Customer;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.application.representation.CustomerDTO;
import com.udacity.critter.application.representation.EmployeeDTO;
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
    public ResponseEntity<?> save(@RequestBody Employee data) {
        try {
            Employee employee = employees.add(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<?> get(@PathVariable long employeeId) {
        try {
            Employee employee = employees.findById(employeeId);
            return ResponseEntity.ok(employee);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
