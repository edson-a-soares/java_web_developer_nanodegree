package com.udacity.critter.domain.model.schedule;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.List;
import javax.persistence.*;
import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.udacity.critter.domain.model.pet.Pet;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;
import com.udacity.critter.infrastructure.service.EmployeeSkillAttributeConverter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
