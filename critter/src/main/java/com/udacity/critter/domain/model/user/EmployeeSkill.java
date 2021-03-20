package com.udacity.critter.domain.model.user;

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

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

}
