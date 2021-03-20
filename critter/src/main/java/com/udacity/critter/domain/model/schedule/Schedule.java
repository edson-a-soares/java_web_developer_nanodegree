package com.udacity.critter.domain.model.schedule;

import java.util.Set;
import java.util.List;
import javax.persistence.*;
import java.time.LocalDate;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;
import com.udacity.critter.infrastructure.service.EmployeeSkillAttributeConverter;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @Column(name = "schedule_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "delivery_date")
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "schedules_pets",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "schedules_employees",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @Column(name = "activities")
    @Convert(converter = EmployeeSkillAttributeConverter.class)
    private Set<EmployeeSkill> activities;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
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

    public long getId() {
        return id;
    }

}
