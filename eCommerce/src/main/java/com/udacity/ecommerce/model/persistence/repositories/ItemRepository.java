package com.udacity.ecommerce.model.persistence.repositories;

import java.util.List;

import com.udacity.ecommerce.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByName(String name);

}
