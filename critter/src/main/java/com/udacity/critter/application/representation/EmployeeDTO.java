package com.udacity.critter.application.representation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.time.DayOfWeek;

import com.udacity.critter.domain.model.user.Customer;
import org.modelmapper.ModelMapper;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;

public class EmployeeDTO {

    private long id;
    private String name;
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;

    public EmployeeDTO() {}

    private EmployeeDTO(RepresentationBuilder builder) {
        id              = builder.id;
        name            = builder.name;
        skills          = builder.skills;
        daysAvailable   = builder.daysAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    /**
     * It builds an entity out of its DTO representation.
     */
    public static class EntityBuilder {

        private EmployeeDTO representation;

        public EntityBuilder() {}

        public EntityBuilder from(EmployeeDTO dto) {
            representation = dto;
            return this;
        }

        public Employee build() {
            ModelMapper modelMapper = new ModelMapper();
            Employee entity = modelMapper.map(representation, Employee.class);

            entity.setSkills(representation.getSkills());
            entity.setDaysAvailable(representation.getDaysAvailable());
            return entity;
        }

    }

    /**
     * It builds a representation collections out of its Entity collection.
     */
    public static class CollectionBuilder {

        private Collection<Employee> employees;

        public CollectionBuilder from(Collection<Employee> collection) {
            employees = collection;
            return this;
        }

        public List<EmployeeDTO> build() {
            List<EmployeeDTO> asList = new ArrayList<>();
            if (employees == null || employees.isEmpty())
                return asList;

            for (Employee entity : employees)
                asList.add(new RepresentationBuilder().from(entity).build());

            return asList;
        }

    }

    /**
     * It builds a single representation out of its Entity.
     */
    public static class RepresentationBuilder {

        private long id;
        private String name;
        private Set<EmployeeSkill> skills;
        private Set<DayOfWeek> daysAvailable;

        public RepresentationBuilder from(Employee entity) {
            id              = entity.getId();
            name            = entity.getName();
            skills          = entity.getSkills();
            daysAvailable   = entity.getDaysAvailable();
            return this;
        }

        public EmployeeDTO build() {
            return new EmployeeDTO(this);
        }

    }

}
