package com.project.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity{


	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column(name = "Title",length = 50)
	private String title;
    @Column(name = "Descreption",length = 255)
	private String descreption;
    @Column(name = "Price")
	private String price;
    @Column(name = "Quantity")
	private String quantity;
   
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
    

    
	public Product(String title, String descreption, String price, String quantity,
			Category category) {

		this.title = title;
		this.descreption = descreption;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
     }
}
