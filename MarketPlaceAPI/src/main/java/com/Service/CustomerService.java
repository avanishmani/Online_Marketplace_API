package com.Service;

import java.util.List;

import com.Entity.Address;
import com.Entity.CurrentUserSession;
import com.Entity.Customer;
import com.Entity.CustomerDto;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;

public interface CustomerService {
	public Customer signUpCustomer(Customer customer) throws CustomerException;

	public Customer updateCustomerDetails(Customer customer, String key) throws CustomerException, LoginException;

	public Customer addAddress(Address address, String key) throws CustomerException, LoginException;

	public Customer deleteAddress(Integer addressId, String key)
			throws CustomerException, LoginException, AddressException;

	public Customer deleteCustomerAccount(String key) throws CustomerException, LoginException;

	public Customer findCustomerByCustomerUserName(String customerUserName) throws CustomerException;

	public Customer findCustomerByCustomerId(Integer customerId) throws CustomerException;

	public List<Customer> findAllCustomers() throws CustomerException;

	public CurrentUserSession loginCustomer(CustomerDto customer) throws LoginException;

	public String logoutCustomer(String key) throws LoginException;
}
