package com.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.Address;
import com.Entity.Cart;
import com.Entity.CurrentUserSession;
import com.Entity.Customer;
import com.Entity.Order;
import com.Entity.OrderStatus;
import com.Entity.Product;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Exception.OrderException;
import com.Exception.ProductException;
import com.Repository.AddressRepo;
import com.Repository.CartRepo;
import com.Repository.CurrentUserSessionRepo;
import com.Repository.CustomerRepo;
import com.Repository.OrderRepository;
import com.Repository.ProductRepository;

@Service
public class OrderServicesImpl implements OrdersServices {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CustomerRepo customerrepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private OrderRepository OrderRepo;

	@Override
	public Order placeOrder(Order Order, Integer addressId, String key)
			throws ProductException, AddressException, CustomerException, OrderException, LoginException {
		Optional<Order> opt = OrderRepo.findById(Order.getOrderId());
		if (opt.isPresent()) {
			throw new OrderException("Order with this order ID already placed => " + Order.getOrderId());
		}
		Optional<Address> aopt = addressRepo.findById(addressId);
		if (aopt.isEmpty()) {
			throw new AddressException("No address found with this address ID => " + addressId);
		}
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can order products please log in as customer ");
		}

		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if (copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
		Customer customer = copt.get();
		Cart cart = customer.getCart();
		Map<String, Integer> products = cart.getProducts();
		Address adr = new Address();
		if (customer.getAddresses().contains(aopt.get())) {
			adr = aopt.get();
		} else {
			throw new AddressException("You don't have any address with this address ID  => " + addressId);
		}

		if (products.isEmpty()) {
			throw new ProductException("No product found ");
		} else {
			Order.setOrderedProducts(products);
			Order.setDeliveryDate(Order.getOrderDate().plusDays(7));
			Order.setCustomer(customer);
			Order.setDeliveryAddress(null);
			for (String i : Order.getOrderedProducts().keySet()) {
				Product p = productRepo.findByProductName(i);
				p.setQuantity(p.getQuantity() - Order.getOrderedProducts().get(i));
				productRepo.save(p);
			}
			cart.setProducts(null);
			cartRepo.save(cart);
			return OrderRepo.save(Order);
		}
	}

	@Override
	public Order cancelOrder(Integer orderId, String key)
			throws ProductException, OrderException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Only customer can cancel the order please log in as customer ");
		}

		Optional<Customer> copt = customerrepo.findById(loggedInUser.getUserId());
		if (copt.isEmpty()) {
			throw new CustomerException("No customer data found with this ID ");
		}
		Customer customer = copt.get();

		Optional<Order> oopt = OrderRepo.findById(orderId);
		if (oopt.isEmpty()) {
			throw new OrderException("No Order found with this order ID => " + orderId);
		}

		Order Order = oopt.get();

		for (String i : Order.getOrderedProducts().keySet()) {
			Product p = productRepo.findByProductName(i);
			p.setQuantity(p.getQuantity() + Order.getOrderedProducts().get(i));
			productRepo.save(p);
		}

		OrderRepo.delete(Order);

		return Order;
	}

	@Override
	public List<Order> getAllOrders(String key) throws OrderException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Only admin can access all order details by all customers ");
		}
		List<Order> allOrder = OrderRepo.findAll();
		if (allOrder.isEmpty()) {
			throw new OrderException("No order found ");
		} else {
			return allOrder;
		}
	}

	@Override
	public List<Order> getAllOrdersByCustomer(String key) throws OrderException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to get all order details ");
		}
		List<Order> allOrder = OrderRepo.findBycustomerId(loggedInUser.getUserId());
		if (allOrder.isEmpty()) {
			throw new OrderException("No order found ");
		} else {
			return allOrder;
		}
	}

	@Override
	public Order UpdateDeliveryAddress(Integer orderId, Integer addressId, String key)
			throws OrderException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to update delivery address ");
		}
		Optional<Address> aopt = addressRepo.findById(addressId);
		if (aopt.isEmpty()) {
			throw new AddressException("No address found with this address ID => " + addressId);
		}
		List<Order> allOrder = OrderRepo.findBycustomerId(loggedInUser.getUserId());
		if (allOrder.isEmpty()) {
			throw new OrderException("No order found ");
		} else {
			Optional<Order> oopt = OrderRepo.findById(orderId);
			if (oopt.isEmpty()) {
				throw new OrderException("No order found with this order id");
			} else {
				Order order = oopt.get();
				if (allOrder.contains(order)) {
					Optional<Customer> copt = customerrepo.findById(order.getCustomer().getCustomerId());
					if (copt.isEmpty()) {
						throw new CustomerException("No customer data found with this ID ");
					}
					Customer customer = copt.get();
					Address adr = new Address();
					if (customer.getAddresses().contains(aopt.get())) {
						adr = aopt.get();
					} else {
						throw new AddressException("You don't have any address with this address ID  => " + addressId);
					}
					order.setDeliveryAddress(null);
					OrderRepo.save(order);
					return order;
				} else {
					throw new OrderException("No order found with this order id");
				}
			}
		}
	}

	@Override
	public Order UpdateDeliveryStatus(Integer orderId, String status, String key)
			throws OrderException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as admin to update delivery status ");
		}
		OrderStatus Ordertatus = null;

		try {
			Ordertatus = Ordertatus.valueOf(status);
		} catch (Exception e) {
			throw new OrderException("Order status can only be NotShipped or Shipped or Delivered");
		}
		Optional<Order> oopt = OrderRepo.findById(orderId);
		if (oopt.isEmpty()) {
			throw new OrderException("No order found with this order id");
		} else {
			Order order = oopt.get();
			order.setOrderStatus(Ordertatus.getOrderStatus());
			OrderRepo.save(order);
			return order;
		}
	}

	@Override
	public Order UpdateDeliveryDate(LocalDate date, Integer orderId, String key)
			throws OrderException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as customer to update delivery date ");
		}
		Customer customer = customerrepo.findById(loggedInUser.getUserId()).get();

		Optional<Order> oopt = OrderRepo.findById(orderId);
		if (oopt.isEmpty()) {
			throw new OrderException("No order found with this order ID ");
		} else {
			Order order = oopt.get();
			List<Order> Order = OrderRepo.findBycustomerId(customer.getCustomerId());
			if (Order.contains(order)) {
				if (date.isAfter(order.getDeliveryDate())) {
					order.setDeliveryDate(date);
					return OrderRepo.save(order);
				} else {
					throw new OrderException("Order can't be delivered before " + order.getDeliveryDate());
				}
			} else {
				throw new OrderException("Loged-in customer does not have any order with this order ID ");
			}
		}
	}

	@Override
	public List<Order> UpdateDeliveryStatusByOrderdate(LocalDate date, String status, String key)
			throws OrderException, CustomerException, LoginException, AddressException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Log in as admin to update delivery status ");
		}
		OrderStatus Ordertatus = null;

		try {
			Ordertatus = Ordertatus.valueOf(status);
		} catch (Exception e) {
			throw new OrderException("Order status can only be NotShipped or Shipped or Delivered");
		}
		List<Order> oopt = OrderRepo.findByOrderDate(date);
		if (oopt.isEmpty()) {
			throw new OrderException("No order found for this order date => " + date);
		} else {
			for (Order i : oopt) {
				i.setOrderStatus(Ordertatus.getOrderStatus());
				OrderRepo.save(i);
			}
			return oopt;
		}
	}

	@Override
	public List<Order> cancelOrdersByOrderDate(LocalDate date, String key)
			throws ProductException, OrderException, CustomerException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Entered current user session key is invalid ");
		}

		if (!loggedInUser.getAdmin()) {
			throw new CustomerException("Only admin can cancel the all the Order Placed on particular date  ");
		}

		List<Order> Order = OrderRepo.findByOrderDate(date);
		if (Order.isEmpty()) {
			throw new OrderException("No Order found with on this date => " + date);
		}

		for (Order j : Order) {

			for (String i : j.getOrderedProducts().keySet()) {
				Product p = productRepo.findByProductName(i);
				p.setQuantity(p.getQuantity() + j.getOrderedProducts().get(i));
				productRepo.save(p);
			}

			OrderRepo.delete(j);
		}
		return Order;
	}

}
