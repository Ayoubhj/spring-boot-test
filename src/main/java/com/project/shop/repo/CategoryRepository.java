package com.project.shop.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Optional<Category> findById(Long id);
	
	
}
