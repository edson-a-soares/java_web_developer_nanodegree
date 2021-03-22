package com.udacity.ecommerce.model.persistence.repositories;

import com.udacity.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
