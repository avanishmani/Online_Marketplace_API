package com.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;
	@Size(min = 2, max = 5, message = "Entered street number is invalid")
	private String streetNo;
	@Size(min = 2, max = 20, message = "Entered building name is invalid")
	private String buildingName;
	@Size(min = 2, max = 20, message = "Entered city name is invalid")
	private String city;
	@Size(min = 2, max = 20, message = "Entered state name is invalid")
	private String state;
	@Size(min = 2, max = 20, message = "Entered country name is invalid")
	private String country;
	@Pattern(regexp = "^[1-9]{6}", message = "Entered pin code is invalid ")
	private String pincode;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId")
	@JsonIgnore
	private Customer customer;
	
}
