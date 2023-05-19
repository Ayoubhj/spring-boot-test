package com.project.shop.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;
import com.project.shop.request.ProductRequest;

@Service
public interface ProductService {


	Collection<Product> getAllProducts();
	
	Product getAllProducts(Long number);
	
	ObjectNode createdProduct(ProductRequest request);
	
	ObjectNode uploadProductImage(MultipartFile multipartFile,Long id);
	
	ObjectNode UpdateProduct(ProductRequest request,Long id);
	
	ObjectNode DeleteProduct(Long id);
	
	Boolean isValid(ProductRequest product);
}
