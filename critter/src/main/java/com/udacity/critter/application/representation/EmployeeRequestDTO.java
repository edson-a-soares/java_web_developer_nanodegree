package com.udacity.critter.application.representation;

import java.util.Set;
import java.time.LocalDate;
import com.udacity.critter.domain.model.user.EmployeeSkill;

public class EmployeeRequestDTO {

    private LocalDate date;
    private Set<EmployeeSkill> skills;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
