package com.udacity.ecommerce.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final static int PASSWORD_MINIMUM_SIZE = 7;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> findByUsername(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		Cart cart = new Cart();
		cartRepository.save(cart);

		user.setCart(cart);
		user.setUsername(createUserRequest.getUsername());

		if (!meetsTheRequirements(createUserRequest.getPassword()) ||
			!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.info("INFO", "UserController | CreateUser | Password did not meet minimum requirements.");
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		userRepository.save(user);

		user.setPassword(null);
		log.info("INFO", "UserController | CreateUser | Success: | username: " + user.getUsername());
		return ResponseEntity.ok(user);
	}

	private boolean meetsTheRequirements(String password) {
		return password != null && password.length() > PASSWORD_MINIMUM_SIZE;
	}

}
