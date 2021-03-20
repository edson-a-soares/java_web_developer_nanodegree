package com.udacity.critter.application.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Service;
import com.udacity.critter.domain.model.schedule.Schedule;
import com.udacity.critter.domain.model.pet.PetRepositoryInterface;
import com.udacity.critter.domain.model.user.CustomerRepositoryInterface;
import com.udacity.critter.domain.model.user.EmployeesRepositoryInterface;
import com.udacity.critter.domain.model.schedule.ScheduleRepositoryInterface;

@Service
public class ScheduleService {

    private final PetRepositoryInterface pets;
    private final CustomerRepositoryInterface customers;
    private final ScheduleRepositoryInterface schedules;
    private final EmployeesRepositoryInterface employees;

    public ScheduleService(
        PetRepositoryInterface petRepository,
        CustomerRepositoryInterface customerRepository,
        ScheduleRepositoryInterface scheduleRepository,
        EmployeesRepositoryInterface employeeRepository
    ) {
        pets = petRepository;
        customers = customerRepository;
        schedules = scheduleRepository;
        employees = employeeRepository;
    }

    public Collection<Schedule> list() {
        return schedules.list();
    }

    public Schedule create(Schedule entity, List<Long> petsIds, List<Long> employeesIds) {
        entity.setPets(new ArrayList<>(pets.findById(petsIds)));
        entity.setEmployees(new ArrayList<>(employees.findById(employeesIds)));

        return schedules.add(entity);
    }

    public List<Schedule> findByCustomer(long id) {
        return new ArrayList<Schedule>(schedules.findByCustomer(id));
    }

}
