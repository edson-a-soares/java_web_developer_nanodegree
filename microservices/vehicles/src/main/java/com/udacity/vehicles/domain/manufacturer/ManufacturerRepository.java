package com.udacity.vehicles.domain.manufacturer;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer>
{}
