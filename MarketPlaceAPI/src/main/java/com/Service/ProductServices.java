package com.Service;

import java.util.List;

import com.Entity.Product;
import com.Exception.AdminException;
import com.Exception.CategoryException;
import com.Exception.LoginException;
import com.Exception.ProductException;

public interface ProductServices {
	public Product addProduct(Product product, String categoryName, String key)
			throws ProductException, CategoryException, AdminException, LoginException;

	public Product updateProduct(Product product, String key) throws ProductException, AdminException, LoginException;

	public Product deleteProduct(Integer productId, String categoryName, String key)
			throws ProductException, CategoryException, AdminException, LoginException;

	public Product getProductByProductName(String productName) throws ProductException;

	public List<Product> getProductsByCategory(String categoryName) throws ProductException, CategoryException;

	public List<Product> getAllProducts() throws ProductException;
}
