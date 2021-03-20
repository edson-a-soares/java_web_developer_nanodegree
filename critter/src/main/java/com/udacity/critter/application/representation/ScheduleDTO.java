package com.udacity.critter.application.representation;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import com.udacity.critter.domain.model.user.Customer;
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

    private ScheduleDTO(RepresentationBuilder builder) {
        id          = builder.id;
        date        = builder.date;
        petIds      = builder.petIds;
        employeeIds = builder.employeeIds;
        activities  = builder.activities;
    }

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

    /**
     * It builds an entity out of its DTO representation.
     */
    public static class EntityBuilder {

        private ScheduleDTO representation;

        public EntityBuilder() {}

        public EntityBuilder from(ScheduleDTO dto) {
            representation = dto;
            return this;
        }

        public Schedule build() {
            ModelMapper modelMapper = new ModelMapper();
            Schedule entity = modelMapper.map(representation, Schedule.class);
            entity.setActivities(representation.activities);
            return entity;
        }

    }

    /**
     * It builds a representation collections out of its Entity collection.
     */
    public static class CollectionBuilder {

        private Collection<Schedule> schedules;

        public CollectionBuilder from(Collection<Schedule> collection) {
            schedules = collection;
            return this;
        }

        public List<ScheduleDTO> build() {
            List<ScheduleDTO> asList = new ArrayList<>();
            for (Schedule entity : schedules)
                asList.add(new ScheduleDTO.RepresentationBuilder().from(entity).build());

            return asList;
        }

    }

    /**
     * It builds a single representation out of its Entity.
     */
    public static class RepresentationBuilder {

        private long id;
        private LocalDate date;
        private List<Long> petIds;
        private List<Long> employeeIds;
        private Set<EmployeeSkill> activities;

        public RepresentationBuilder from(Schedule entity) {
            id          = entity.getId();
            date        = entity.getDate();
            activities  = entity.getActivities();

            petIds = new ArrayList<>();
            for (Pet pet : entity.getPets())
                petIds.add(pet.getId());

            employeeIds = new ArrayList<>();
            for (Employee employee : entity.getEmployees())
                employeeIds.add(employee.getId());

            return this;
        }

        public ScheduleDTO build() {
            return new ScheduleDTO(this);
        }

    }

}
