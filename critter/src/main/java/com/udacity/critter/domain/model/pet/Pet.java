package com.udacity.critter.domain.model.pet;

import javax.persistence.*;
import java.time.LocalDate;
import com.udacity.critter.domain.model.user.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pets")
@JsonIgnoreProperties(value = { "owner" })
public class Pet {

    @Id
    @Column(name = "pet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer owner;

    @Column(name = "notes")
    private String notes;

    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer customer) {
        owner = customer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
