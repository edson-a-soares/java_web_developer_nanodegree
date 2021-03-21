package com.udacity.critter.domain.model.user;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.udacity.critter.domain.model.pet.Pet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="owner", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @Column(name = "phone_number")
    private String phoneNumber;

}
