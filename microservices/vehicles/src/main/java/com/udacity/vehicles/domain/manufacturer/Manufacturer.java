package com.udacity.vehicles.domain.manufacturer;

import javax.persistence.Id;
import javax.persistence.Entity;

/**
 * Declares class to hold car manufacturer information.
 */
@Entity
public class Manufacturer {

    @Id
    private Integer code;
    private String name;

    public Manufacturer() {}

    public Manufacturer(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
