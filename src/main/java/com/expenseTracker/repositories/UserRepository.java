package com.expenseTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenseTracker.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom queries can be added here if needed
	
	User findByEmail(String email);
}
