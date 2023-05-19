package com.project.shop.controllers;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;
import com.project.shop.request.ProductRequest;
import com.project.shop.services.ProductService;

import lombok.AllArgsConstructor;

@RequestMapping(path = "/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
    public Collection<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	
	@GetMapping("{id}")
    public Product getAllProductsById(@PathVariable(value="id") Long number) {
		return productService.getAllProducts(number);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ObjectNode createdProduct(@ModelAttribute  ProductRequest request) {
		
		return productService.createdProduct(request);
	};
	
	

	
	@PutMapping("/{id}")
	public ObjectNode UpdateProduct(@RequestBody ProductRequest request,@PathVariable(value="id") Long id) {
		return productService.UpdateProduct(request, id);

	}
	
	@DeleteMapping("/{id}")
	public ObjectNode DeleteProduct(@RequestParam("id") Long id) {
		return productService.DeleteProduct(id);
	}
}