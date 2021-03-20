package com.udacity.critter.controller;

import java.util.List;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.udacity.critter.domain.model.schedule.Schedule;
import com.udacity.critter.application.service.ScheduleService;
import com.udacity.critter.application.representation.ScheduleDTO;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService schedules;

    public ScheduleController(ScheduleService service) {
        schedules = service;
    }

    @GetMapping
    public Collection<Schedule> list() {
        return schedules.list();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ScheduleDTO representation) {
        try {
            Schedule entity = schedules.create(
                representation.asEntity(),
                representation.getPetIds(),
                representation.getEmployeeIds()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(new ScheduleDTO(entity));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> list = schedules.findByCustomer(customerId);
        return null;
    }

}
