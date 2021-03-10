package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long>
{}
