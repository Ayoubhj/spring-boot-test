package com.project.shop.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.shop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { 
	
	
	 Optional<Product> findById(Long id);
	 
	 List<Product> getProductByCategoryId(Long id);
}
