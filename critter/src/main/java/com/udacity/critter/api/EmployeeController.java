package com.udacity.critter.api;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.DayOfWeek;
import java.util.Collection;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.application.service.EmployeeService;
import com.udacity.critter.application.representation.EmployeeDTO;
import com.udacity.critter.application.representation.EmployeeRequestDTO;

@RestController
@RequestMapping("/user")
public class EmployeeController {

    private final EmployeeService employees;

    public EmployeeController(EmployeeService service) {
        this.employees = service;
    }

    @PostMapping("/employee")
    public EmployeeDTO save(@RequestBody EmployeeDTO representation) {
        Employee employee = new EmployeeDTO
            .EntityBuilder()
            .from(representation)
            .build();

        Employee entity = employees.create(employee);
        return new EmployeeDTO
            .RepresentationBuilder()
            .from(entity)
            .build();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO get(@PathVariable long employeeId) {
        Employee entity = employees.findById(employeeId);

        return new EmployeeDTO
            .RepresentationBuilder()
            .from(entity)
            .build();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employees.findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employees.create(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO representation) {
        Collection<Employee> employees = this.employees.findByAvailability(representation.getSkills(), representation.getDate());

        return new EmployeeDTO
            .CollectionBuilder()
            .from(employees)
            .build();
    }

}
