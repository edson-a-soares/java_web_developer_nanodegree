package com.udacity.critter.infrastructure.persistence;

import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.persistence.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import com.udacity.critter.domain.model.user.Employee;
import com.udacity.critter.domain.model.user.EmployeeSkill;
import com.udacity.critter.domain.model.user.EmployeesRepositoryInterface;

@Component
public class EmployeeRepository implements EmployeesRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Employee add(Employee employee) {
        return manager.merge(employee);
    }

    @Override
    public Employee findById(long id) {
        Employee employee = manager.find(Employee.class, id);
        if (employee == null)
            throw new EntityNotFoundException();

        return employee;
    }

    @Override
    public List<Employee> findById(List<Long> ids) {
        TypedQuery<Employee> query = manager.createQuery(
            "SELECT e FROM Employee e WHERE e.id IN :ids",
            Employee.class
        ).setParameter("ids", ids);

        return query.getResultList();
    }

    @Override
    public List<Employee> findByAvailability(Set<EmployeeSkill> skills, LocalDate date) {

        // Do the asymptotic analysis of this query afterwards.
        Query query = manager.createNativeQuery(
            new StringBuilder()
                .append(" SELECT DISTINCT employees.*")
                .append("    FROM employees")
                .append("    JOIN employees_skills es ON employees.employee_id = es.employee_id")
                .append("    JOIN employees_availability ea ON employees.employee_id = ea.employee_id")
                .append(" WHERE es.skill_name IN :skills")
                .append(" AND ea.weekday = :dayOfWeek")
                .append(" AND employees.employee_id NOT IN ( ")
                .append("    SELECT schedules_employees.employee_id")
                .append("       FROM schedules")
                .append("       JOIN schedules_employees ON schedules.schedule_id = schedules_employees.schedule_id")
                .append("     WHERE delivery_date = :date")
                .append(")")
                .toString(),
            Employee.class
        )
        .setParameter("date",       date.toString())
        .setParameter("skills",     asListOfStrings(skills))
        .setParameter("dayOfWeek",  getWeekdayName(date));

        return query.getResultList();
    }

    private String getWeekdayName(LocalDate date) {
        DayOfWeek weekday = date.getDayOfWeek();
        return weekday.name();
    }

    private List<String> asListOfStrings(Set<EmployeeSkill> skills) {
        if (skills == null || skills.isEmpty())
            return Collections.singletonList("");

        return skills.stream()
            .map(EmployeeSkill::name)
            .collect(Collectors.toList());
    }

}
