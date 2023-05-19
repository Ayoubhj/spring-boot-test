package com.project.shop.services.impl;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.shop.entity.Product;
import com.project.shop.exception.FileStorageException;
import com.project.shop.repo.ProductRepository;
import com.project.shop.request.ProductRequest;
import com.project.shop.services.ProductService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class ProductImpl implements ProductService{
	
	@Autowired
	private  final ProductRepository productRepo;

	// @Autowired
	// private final Path fileStorageLocation;

 
    
	@Override
	public Collection<Product> getAllProducts() {

		List<Product> productes = productRepo.findAll();

		return productes;
	}

	@Override
	public ObjectNode createdProduct(ProductRequest request) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();
		
		if(isValid(request) == false) {
			
			return objectNode.put("validation Error", "Please Fill The Form");
			
		}
 
	

		Product product = new Product(
				request.getTitle(), 
				request.getDescreption(), 
				request.getPrice(),
				request.getQuantity(), 
				request.getCategory(), null
				
				);

		productRepo.save(product);
		
		uploadProductImage(request.getImage(),product.getId());
	   
		
		return objectNode.put("message", "Product Created  Successfuly");
	
	}

	@Override
	public ObjectNode UpdateProduct(ProductRequest request, Long id) {

      
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

	@Override
	public Product getAllProducts(Long number) {
		Product product = productRepo.get_products_by_id(number);
		return product;
	}

	@Override
	public Boolean isValid(ProductRequest product) {
		
		
			
			if(product.getTitle() == "" || product.getDescreption() == "" || product.getPrice() == "" || product.getQuantity() == "" || product.getCategory().getId() == null ) {
				
				return false;
				
			}
			
		
		return true;
	}

	@Override
	public ObjectNode uploadProductImage(MultipartFile file,Long id) {

		ObjectMapper mapper = new ObjectMapper();

		ObjectNode objectNode = mapper.createObjectNode();

		
		@SuppressWarnings("deprecation")
		Product product = productRepo.getOne(id) ;
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
          //  Path targetLocation = fileStorageLocation.resolve(fileName);
        //    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
 
            product.setImage(fileName);
            
            productRepo.save(product);
            return objectNode.put("message", "Uploaded");
            
      
	}
}
