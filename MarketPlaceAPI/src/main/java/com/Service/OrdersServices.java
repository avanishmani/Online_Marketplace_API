package com.Service;

import java.time.LocalDate;
import java.util.List;

import com.Entity.Order;
import com.Exception.AddressException;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Exception.OrderException;
import com.Exception.ProductException;

public interface OrdersServices {
	public Order placeOrder(Order orders,Integer addressId,String key) throws ProductException,AddressException,OrderException,CustomerException, LoginException;
	public Order cancelOrder(Integer orderId,String key) throws ProductException,OrderException,CustomerException, LoginException;
	public List<Order> getAllOrders(String key) throws OrderException,CustomerException, LoginException;
	public List<Order> getAllOrdersByCustomer(String key) throws OrderException,CustomerException, LoginException;
	public Order UpdateDeliveryAddress(Integer orderId,Integer addressId,String key) throws OrderException,CustomerException, LoginException,AddressException;
	public Order UpdateDeliveryStatus(Integer orderId,String status,String key) throws OrderException,CustomerException, LoginException,AddressException;
	public Order UpdateDeliveryDate(LocalDate date, Integer orderId,String key) throws OrderException,CustomerException, LoginException,AddressException;
	public List<Order> UpdateDeliveryStatusByOrderdate(LocalDate date,String status,String key) throws OrderException,CustomerException, LoginException,AddressException;
	public List<Order> cancelOrdersByOrderDate(LocalDate date,String key) throws ProductException,OrderException,CustomerException, LoginException;
}
