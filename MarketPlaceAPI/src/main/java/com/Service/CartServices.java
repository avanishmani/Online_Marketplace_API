package com.Service;

import java.util.Map;

import com.Entity.Cart;
import com.Exception.CustomerException;
import com.Exception.LoginException;
import com.Exception.ProductException;

public interface CartServices {
	public Cart addProductToCart(String productName, Integer quantity ,String key) throws ProductException,CustomerException, LoginException;
	public Cart updateProductQuantity(String productName, Integer quantity ,String key) throws ProductException,CustomerException, LoginException;
	public Cart deleteProductFromCart(String productName,String key) throws ProductException,CustomerException, LoginException;
	public Map<String,Integer> getAllProductsInCart(String key) throws ProductException,CustomerException, LoginException;
	public Cart removeAllProductsFromCart(String key) throws ProductException,CustomerException, LoginException;
	
}
