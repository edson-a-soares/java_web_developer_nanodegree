package com.udacity.critter.application.representation;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import org.modelmapper.ModelMapper;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.schedule.Schedule;
import com.udacity.critter.domain.model.user.EmployeeSkill;

public class ScheduleDTO {

    private long id;
    private LocalDate date;
    private List<Long> petIds;
    private List<Long> employeeIds;
    private Set<EmployeeSkill> activities;

    public ScheduleDTO() {}

    public ScheduleDTO(Schedule entity) {
        date        = entity.getDate();
        activities  = entity.getActivities();

        petIds = new ArrayList<>();
        for (Pet pet : entity.getPets())
            petIds.add(pet.getId());

        employeeIds = new ArrayList<>();
        for (Employee employee : entity.getEmployees())
            employeeIds.add(employee.getId());
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public Schedule asEntity() {
        ModelMapper modelMapper = new ModelMapper();
        Schedule entity = modelMapper.map(this, Schedule.class);

        entity.setActivities(activities);
        return entity;
    }

}
