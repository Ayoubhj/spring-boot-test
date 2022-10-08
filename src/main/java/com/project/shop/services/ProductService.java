package com.project.shop.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;

@Service
public interface ProductService {


	Collection<Product> getAllProducts();
	
	ObjectNode createdProduct(Product request);
	
	ObjectNode UpdateProduct(Product request,Long id);
	
	ObjectNode DeleteProduct(Long id);
}
