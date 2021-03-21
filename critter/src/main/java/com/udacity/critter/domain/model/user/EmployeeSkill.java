package com.udacity.critter.domain.model.user;

import lombok.Getter;

@Getter
public enum EmployeeSkill {

    PETTING ("PETTING"),
    WALKING ("WALKING"),
    FEEDING ("FEEDING"),
    MEDICATING ("MEDICATING"),
    SHAVING ("SHAVING");

    private final String code;

    EmployeeSkill(String code) {
        this.code = code;
    }

}
