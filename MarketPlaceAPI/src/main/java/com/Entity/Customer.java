package com.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;

	@Size(min = 2, max = 20, message = "Length of name should not be more than 20 characters and less than 2 characters")
	private String customerName;

	@Pattern(regexp = "^[0-9]{10}", message = "Check the mobile number entered and Try again ")
	@Column(unique = true)
	private String customerMobileNumber;

	@Email(message = "Check the e-mail ID entered and try again ")
	@Column(unique = true)
	private String customerUsername;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password should contain at least 1 uppercase character, 1 lower case character, 1 special character, numeric value and length of the password should be more than 8 characters ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String customerPassword;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<Address> addresses = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	private Cart cart;
}
