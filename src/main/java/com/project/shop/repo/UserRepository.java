package com.project.shop.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
