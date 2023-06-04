package com.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.Address;
import com.Entity.CurrentUserSession;
import com.Entity.Customer;
import com.Entity.CustomerDto;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Repository.AddressRepo;
import com.Repository.CurrentUserSessionRepo;
import com.Repository.CustomerRepo;
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepo customerrepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Override
	public Customer signUpCustomer(Customer customer) throws CustomerException {
		Customer existingCustomer = customerrepo.findByCustomerUsername(customer.getCustomerUsername());

		if (existingCustomer != null) {
			throw new CustomerException(
					"Customer already exist with this username => " + customer.getCustomerUsername());
		} else {
			return customerrepo.save(customer);
		}
	}

	@Override
	public Customer updateCustomerDetails(Customer customer, String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update customer details please login first ");
		}

		if (customer.getCustomerId() == loggedInUser.getUserId() && !loggedInUser.getAdmin()) {
			return customerrepo.save(customer);
		} else {
			throw new CustomerException(
					"Provided customer details are incorrect please check CustomerException ID and other details and try again ");
		}
	}

	@Override
	public Customer addAddress(Address address, String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update customer details please login first ");
		} else if (!loggedInUser.getAdmin()) {
			Optional<Customer> opt = customerrepo.findById(loggedInUser.getUserId());
			Customer ccustomer = opt.get();
			ccustomer.getAddresses().add(address);
			address.setCustomer(ccustomer);
			customerrepo.save(ccustomer);
			return ccustomer;
		} else {
			throw new CustomerException("Login with customer account to add address ");
		}
	}

	@Override
	public Customer deleteCustomerAccount(String key) throws CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To delete customer account login first ");
		} else if (!loggedInUser.getAdmin()) {
			Optional<Customer> opt = customerrepo.findById(loggedInUser.getUserId());
			Customer ccustomer = opt.get();
			customerrepo.delete(ccustomer);
			return ccustomer;
		} else {
			throw new CustomerException("Login with customer account to delete customer account ");
		}

	}

	@Override
	public Customer findCustomerByCustomerUserName(String customerUserName) throws CustomerException {
		Customer existingCustomer = customerrepo.findByCustomerUsername(customerUserName);

		if (existingCustomer == null) {
			throw new CustomerException("No customer found with this customer user name => " + customerUserName);
		} else {
			return existingCustomer;
		}
	}

	@Override
	public Customer findCustomerByCustomerId(Integer customerId) throws CustomerException {
		Optional<Customer> opt = customerrepo.findById(customerId);

		if (opt.isEmpty()) {
			throw new CustomerException("No customer found with this customer ID => " + customerId);
		} else {
			return opt.get();
		}
	}

	@Override
	public List<Customer> findAllCustomers() throws CustomerException {
		List<Customer> customerlist = customerrepo.findAll();
		if (customerlist.isEmpty()) {
			throw new CustomerException("No customer found");
		} else {
			return customerlist;
		}
	}

	@Override
	public CurrentUserSession loginCustomer(CustomerDto customer) throws LoginException {
		Customer existingUser = customerrepo.findByCustomerUsername(customer.getCustomerUsername());

		if (existingUser == null) {
			throw new LoginException("No customer found with this username => " + customer.getCustomerUsername());
		}

		Optional<CurrentUserSession> validCustomerSessionOpt = currentUserSessionRepo
				.findById(existingUser.getCustomerId());

		if (validCustomerSessionOpt.isPresent()
				&& existingUser.getCustomerPassword().equals(customer.getCustomerPassword())) {
			currentUserSessionRepo.delete(validCustomerSessionOpt.get());
		}

		if (existingUser.getCustomerPassword().equals(customer.getCustomerPassword())) {

			// String key = RandomGenerator.getDefault().;
			int targetStringLength = 6;
			RandomGenerator generator = RandomGenerator.getDefault();
			String key = generator.ints('0', 'z' + 1).filter(i -> (i <= '9' || i >= 'A') && (i <= 'Z' || i >= 'a'))
					.limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			Boolean isAdmin = false;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getCustomerId(), key, isAdmin,
					LocalDateTime.now());

			currentUserSessionRepo.save(currentUserSession);

			return currentUserSession;
		} else {
			throw new LoginException("Password entered is incorrect ");
		}
	}

	@Override
	public String logoutCustomer(String key) throws LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid current user session key ");
		}
		currentUserSessionRepo.delete(loggedInUser);
		return "Logged Out !";
	}

	@Override
	public Customer deleteAddress(Integer addressId, String key)
			throws CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update customer details please login first ");
		} else if (!loggedInUser.getAdmin()) {
			Optional<Address> aopt = addressRepo.findById(addressId);
			Optional<Customer> opt = customerrepo.findById(loggedInUser.getUserId());
			Customer ccustomer = opt.get();
			if (aopt.isEmpty()) {
				throw new AddressException("No address found with this address ID ");
			} else {
				Address adr = aopt.get();
				ccustomer.getAddresses().remove(adr);
				addressRepo.delete(adr);
				return ccustomer;
			}
		} else {
			throw new CustomerException("Login with customer account to add address ");
		}
	}
}
