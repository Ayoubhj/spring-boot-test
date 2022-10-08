package com.project.shop.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Category;

import com.project.shop.repo.CategoryRepository;
import com.project.shop.services.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryImlp implements CategoryService {

	private final CategoryRepository categoryRepo;


	@Override
	public Collection<Category> getAllCategories() {

		List<Category> categories = categoryRepo.findAll();

		
		return categories;

	}

	@Override
	public ObjectNode createdCategory(Category request) {


		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		Category category = new Category(request.getName());

		categoryRepo.save(category);

	    return objectNode.put("message", "Category Created  Successfuly");
	    
	}

	@Override
	public ObjectNode UpdateCategory(Category request, Long id) {
		Optional<Category> category = categoryRepo.findById(id);
 

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		if (category.isPresent()) {
			category.get().setName(request.getName());

			categoryRepo.save(category.get());
		} else {

			return objectNode.put("message", "Category Not Found");

		}

		return objectNode.put("message", "Category Updated  Successfuly");
	}

	@Override
	public ObjectNode DeleteCategory(Long id) {


		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		Optional<Category> category = categoryRepo.findById(id);

		if (category.isPresent()) {
			categoryRepo.deleteById(category.get().getId());
		} else {

			return objectNode.put("message", "Category Not Found");

		}

		return objectNode.put("message", "Category Deleted  Successfuly");
	}

	@Override
	public Collection<Category> getAllCategoriesWithProducts() {
		// TODO Auto-generated method stub
       List<Category> categories = categoryRepo.findAll();

		
		return categories;
	}

}
