package com.udacity.critter.api;

import java.util.List;
import java.util.Collection;
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
    public List<ScheduleDTO> list() {
        Collection<Schedule> list = schedules.list();

        return new ScheduleDTO
            .CollectionBuilder()
            .from(list)
            .build();
    }

    @PostMapping
    public ScheduleDTO create(@RequestBody ScheduleDTO representation) {
        Schedule entity = new ScheduleDTO
            .EntityBuilder()
            .from(representation)
            .build();

        Schedule schedule = schedules.create(entity, representation.getPetIds(), representation.getEmployeeIds());
        return new ScheduleDTO
            .RepresentationBuilder()
            .from(schedule)
            .build();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Collection<Schedule> schedules = this.schedules.findByPet(petId);

        return new ScheduleDTO
            .CollectionBuilder()
            .from(schedules)
            .build();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Collection<Schedule> schedules = this.schedules.findByEmployee(employeeId);

        return new ScheduleDTO
            .CollectionBuilder()
            .from(schedules)
            .build();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Collection<Schedule> schedules = this.schedules.findByCustomer(customerId);

        return new ScheduleDTO
            .CollectionBuilder()
            .from(schedules)
            .build();
    }

}
