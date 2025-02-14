package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repository.UserRepository;
import com.scm.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository repository;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	public User saveUser(User user) {
		// Generate a user ID
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);

		// Encode the user's password securely
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		// Avoid logging the encoded password in production code
		logger.debug("Password encoded for user: {}", user.getUserId());

		// Set the user role
		user.setRoleList(List.of(AppConstants.Roll_USER)); // Ensure AppConstants.ROLE_USER is a valid role constant

		// Log the provider (make sure provider is not null)
		if (user.getProvider() != null) {
			logger.info("User provider: {}", user.getProvider());
		} else {
			logger.warn("User provider is null for user: {}", user.getUserId());
		}

		// Save and return the user
		return repository.save(user);
	}

	@Override
	public Optional<User> getUserById(String id) {
		User user = this.repository.findById(id).orElse(null);
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> updatUser(User user) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUserExist(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  User getUserByEmail(String email) {
		 return this.repository.findByEmail(email).orElse(null);
		
	}

}
