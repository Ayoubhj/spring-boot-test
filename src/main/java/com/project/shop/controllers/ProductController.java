package com.project.shop.controllers;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;
import com.project.shop.services.ProductService;

import lombok.AllArgsConstructor;

@RequestMapping(path = "/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@GetMapping
    public Collection<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@PostMapping
	public ObjectNode createdProduct(@RequestBody Product request) {
		
		return productService.createdProduct(request);
	};
	
	@PutMapping("/{id}")
	public ObjectNode UpdateProduct(@RequestBody Product request,@PathVariable(value="id") Long id) {
		return productService.UpdateProduct(request, id);

	}
	
	@DeleteMapping("/{id}")
	public ObjectNode DeleteProduct(@RequestParam("id") Long id) {
		return productService.DeleteProduct(id);
	}
}