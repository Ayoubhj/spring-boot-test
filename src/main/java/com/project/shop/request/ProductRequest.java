package com.project.shop.request;


import org.springframework.web.multipart.MultipartFile;

import com.project.shop.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String title;

	private String descreption;

	private String price;
 
	private String quantity;

	private Category category;
	
   	private MultipartFile image;

	
	
}
