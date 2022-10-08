package com.project.shop.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;
import com.project.shop.repo.ProductRepository;
import com.project.shop.services.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductImpl implements ProductService{
	
	private final ProductRepository productRepo;

	
	
	@Override
	public Collection<Product> getAllProducts() {

		List<Product> productes = productRepo.findAll();

		return productes;
	}

	@Override
	public ObjectNode createdProduct(Product request) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();

		Product product = new Product(request.getTitle(), request.getDescreption(), request.getPrice(),
				request.getQuantity(), request.getCategory());

		productRepo.save(product);
  
		
		return objectNode.put("message", "Product Created  Successfuly");

	}

	@Override
	public ObjectNode UpdateProduct(Product request, Long id) {


		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		Optional<Product> product = productRepo.findById(id);

		if (product.isPresent()) {
			product.get().setTitle(request.getTitle());
			product.get().setDescreption(request.getDescreption());
			product.get().setPrice(request.getPrice());
			product.get().setQuantity(request.getQuantity());
			product.get().setCategory(request.getCategory());
			productRepo.save(product.get());
		} else {

			

			return objectNode.put("message", "Product Not Found");

		}

		

		return objectNode.put("message", "Product Updated  Successfuly");

	}

	@Override
	public ObjectNode DeleteProduct(Long id) {
		Optional<Product> product = productRepo.findById(id);

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		if (product.isPresent()) {
			productRepo.deleteById(product.get().getId());
		} else {
			return objectNode.put("message", "Product Not Found");
		}

		return objectNode.put("message", "Product Deleted  Successfuly");
	}
}
