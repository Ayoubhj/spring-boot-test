package com.project.shop.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Category;

@Service
public interface CategoryService {


	Collection<Category> getAllCategories();
	
	Collection<Category> getAllCategoriesWithProducts();
	
	ObjectNode createdCategory(Category request);
	
	ObjectNode UpdateCategory(Category request,Long id);
	
	ObjectNode DeleteCategory(Long id);
    
}
