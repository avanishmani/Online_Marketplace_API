package com.Controler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Entity.Order;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Exception.OrderException;
import com.Exception.ProductException;
import com.Service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/OrderController")
public class OrdersController {
	@Autowired
	private OrderService Orderervices;

	@PutMapping("/placeOrder")
	public ResponseEntity<Order> placeOrderHandler(@RequestBody Order Order, @RequestParam Integer addressId,
			@RequestParam String key)
			throws ProductException, OrderException, CustomerException, LoginException, AddressException {

		Order neworder = Orderervices.placeOrder(Order, addressId, key);

		return new ResponseEntity<Order>(neworder, HttpStatus.OK);

	}

	@DeleteMapping("/cancelOrder")
	public ResponseEntity<Order> cancelOrderHandler(@RequestParam Integer orderId, @RequestParam String key)
			throws ProductException, OrderException, CustomerException, LoginException {

		Order cancelledOrder = Orderervices.cancelOrder(orderId, key);

		return new ResponseEntity<Order>(cancelledOrder, HttpStatus.OK);
	}

	@GetMapping("/getAllOrder")
	public ResponseEntity<List<Order>> findAllOrderHandler(@RequestParam String key)
			throws OrderException, CustomerException, LoginException {

		List<Order> Order = Orderervices.getAllOrder(key);

		return new ResponseEntity<List<Order>>(Order, HttpStatus.OK);

	}

	@GetMapping("/getAllOrderByCustomerID")
	public ResponseEntity<List<Order>> findAllOrderByCustomerIDHandler(@RequestParam String key)
			throws OrderException, CustomerException, LoginException {

		List<Order> Order = Orderervices.getAllOrderByCustomer(key);

		return new ResponseEntity<List<Order>>(Order, HttpStatus.OK);

	}

	@PutMapping("/UpdateDeliveryAddress")
	public ResponseEntity<Order> updateDeliveryAddressHandler(@RequestParam Integer orderId,
			@RequestParam Integer addressId, @RequestParam("key") String key)
			throws OrderException, CustomerException, LoginException, AddressException {

		Order order = Orderervices.UpdateDeliveryAddress(orderId, addressId, key);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

	@PutMapping("/UpdateDeliveryStatus")
	public ResponseEntity<Order> updateDeliveryStatusHandler(@RequestParam Integer orderId, @RequestParam String status,
			@RequestParam("key") String key)
			throws OrderException, CustomerException, LoginException, AddressException {

		Order order = Orderervices.UpdateDeliveryStatus(orderId, status, key);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

	@PutMapping("/UpdateDeliveryDate")
	public ResponseEntity<Order> updateDeliveryDateHandler(@RequestParam("date") String date,
			@RequestParam Integer orderId, @RequestParam("key") String key)
			throws OrderException, CustomerException, LoginException, AddressException {

		Order order = Orderervices.UpdateDeliveryDate(LocalDate.parse(date), orderId, key);

		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

	@PutMapping("/UpdateDeliveryStatusByOrderdate")
	public ResponseEntity<List<Order>> updateDeliveryStatusByOrderdateHandler(@RequestParam("date") String date,
			@RequestParam String status, @RequestParam("key") String key)
			throws OrderException, CustomerException, LoginException, AddressException {

		List<Order> Order = Orderervices.UpdateDeliveryStatusByOrderdate(LocalDate.parse(date), status, key);

		return new ResponseEntity<List<Order>>(Order, HttpStatus.OK);

	}

	@DeleteMapping("/cancelOrderByOrderDate")
	public ResponseEntity<List<Order>> cancelOrderByOrderDateHandler(@RequestParam("date") String date,
			@RequestParam String key) throws ProductException, OrderException, CustomerException, LoginException {

		List<Order> cancelledOrder = Orderervices.cancelOrderByOrderDate(LocalDate.parse(date), key);

		return new ResponseEntity<List<Order>>(cancelledOrder, HttpStatus.OK);
	}
}
