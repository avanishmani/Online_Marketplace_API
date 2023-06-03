package com.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Size(min = 2, max = 30, message = "Length of name of product should not be more than 30 characters and less than 2 characters")
	@Column(unique = true)
	private String productName;
	
	@Min(value = 1,message = "Price of product can't be less than ₹1 ")
	private double price;
	
	@Size(min = 2, max = 15, message = "Length of color should not be more than 15 characters and less than 2 characters")
	private String color;
	
	@Size(min = 3, max = 15, message = "Length of dimensions should not be more than 15 characters and less than 3 characters")
	private String dimensions;
	
	@Size(min = 10, max = 55, message = "Length of imageUrl should not be more than 55 characters and less than 10 characters")
	private String imageUrl;
	
	@Size(min = 2, max = 20, message = "Length of manufacturer should not be more than 20 characters and less than 2 characters")
	private String manufacturer;
	
	@Min(value = 1,message = "Quantity of product can't be less than 1 ")
	private Integer quantity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private Category category;
}
