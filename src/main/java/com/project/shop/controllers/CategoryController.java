package com.project.shop.controllers;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Category;
import com.project.shop.services.CategoryService;

import lombok.AllArgsConstructor;

@RequestMapping(path = "/api/v1/category")
@RestController
@AllArgsConstructor
public class CategoryController {
  
	private final CategoryService categoryService;
	
	@GetMapping
    public Collection<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	
	@PostMapping(produces = "application/JSON")
	public ObjectNode createdCategory(@RequestBody Category request) {
		
		return categoryService.createdCategory(request);
	};
	
	@PutMapping("/{id}")
	public ObjectNode UpdateCategory(@RequestBody Category request, @RequestParam("id") Long id) {
		
		return categoryService.UpdateCategory(request, id);

	}
	
	@DeleteMapping("/{id}")
	public ObjectNode DeleteProduct(@RequestParam("id") Long id) {
		
		return categoryService.DeleteCategory(id);
	}
}

