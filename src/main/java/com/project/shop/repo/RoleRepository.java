package com.project.shop.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shop.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
 
	Optional<Role> findByName(com.project.shop.enuM.Role name);
	
}
