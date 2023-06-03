package com.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer adminId;
	
	@Size(min = 2, max = 20, message = "Length of name should not be more than 20 characters and less than 2 characters")
	private String adminName;
	
	@Pattern(regexp = "^[0-9]{10}", message = "Check the mobile number entered and Try again ")
	@Column(unique = true)
	private String adminMobileNumber;
	
	@Email(message = "Check the e-mail ID entered and try again ")
	@Column(unique = true)
	private String adminUsername;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password should contain at least 1 uppercase character, 1 lower case character, 1 special character, numeric value and length of the password should be more than 8 characters ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String adminPassword;

}
