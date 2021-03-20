package com.udacity.critter.domain.model.user;

import java.util.Set;
import javax.persistence.*;
import java.time.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.udacity.critter.infrastructure.service.DayOfWeekAttributeConverter;
import com.udacity.critter.infrastructure.service.EmployeeSkillAttributeConverter;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "skill_set")
    @Convert(converter = EmployeeSkillAttributeConverter.class)
    private Set<EmployeeSkill> skills;

    @Column(name = "availability")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Convert(converter = DayOfWeekAttributeConverter.class)
    private Set<DayOfWeek> daysAvailable;

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

}
