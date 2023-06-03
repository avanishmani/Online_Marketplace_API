package com.Controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.Address;
import com.Entity.CurrentUserSession;
import com.Entity.Customer;
import com.Entity.CustomerDto;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Service.CustomerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customerController")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customerSignUp")
	public ResponseEntity<Customer> signUpCustomerHandler(@Valid @RequestBody Customer customer) throws CustomerException{
		
		Customer savedUser = customerService.signUpCustomer(customer);

		return new ResponseEntity<Customer>(savedUser, HttpStatus.OK);
	}
	
	@PutMapping("/updateCustomer")
	public ResponseEntity<Customer> updateCustomerDetailsHandler(@Valid @RequestBody Customer customer, @RequestParam("key") String key) throws CustomerException, LoginException {

		Customer updatedCustomer = customerService.updateCustomerDetails(customer, key);
		
		return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);

	}
	
	@PutMapping("/addAddress")
	public ResponseEntity<Customer> addAddressHandler(@Valid @RequestBody Address address, @RequestParam("key") String key) throws CustomerException, LoginException{

		Customer updatedCustomer = customerService.addAddress(address, key);
		
		return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteAddress")
	public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("addressId") Integer addressId,@RequestParam("key") String key) throws CustomerException, LoginException, AddressException{

		Customer deletedAddress = customerService.deleteAddress(addressId, key);

		return new ResponseEntity<Customer>(deletedAddress, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteCustomer")
	public ResponseEntity<Customer> deleteCustomerHandler(@RequestParam("key") String key)throws CustomerException, LoginException {

		Customer deletedCustomer = customerService.deleteCustomerAccount(key);

		return new ResponseEntity<Customer>(deletedCustomer, HttpStatus.OK);

	}
	
	@GetMapping("/customerByUserName")
	public ResponseEntity<Customer> getCustomerByUserNameHandler(@RequestParam("customerUserName") String customerUserName) throws CustomerException, LoginException {

		Customer customer = customerService.findCustomerByCustomerUserName(customerUserName);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}
	
	@GetMapping("/customerByCustomerId")
	public ResponseEntity<Customer> getCustomerByCustomerIdHandler(@RequestParam("customerId") Integer customerId) throws CustomerException, LoginException {

		Customer customer = customerService.findCustomerByCustomerId(customerId);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}
	
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> findAllCustomersHandler() throws CustomerException{

		List<Customer> allCustomer = customerService.findAllCustomers();

		return new ResponseEntity<List<Customer>>(allCustomer, HttpStatus.OK);

	}
	
	@PostMapping("/loginCustomer")
	public ResponseEntity<CurrentUserSession> loginCustomerHandler(@Valid @RequestBody CustomerDto customer) throws LoginException {

		CurrentUserSession cus = customerService.loginCustomer(customer);

		return new ResponseEntity<CurrentUserSession>(cus, HttpStatus.OK);

	}
	
	@PostMapping("/logoutCustomer")
	public ResponseEntity<String> logoutCustomerHandler(@RequestParam("key") String key) throws LoginException {

		String res = customerService.logoutCustomer(key);

		return new ResponseEntity<String>(res, HttpStatus.OK);

	}
}
