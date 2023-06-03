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

import com.Entity.Product;
import com.Exception.AdminException;
import com.Exception.CategoryException;
import com.Exception.LoginException;
import com.Exception.ProductException;
import com.Service.ProductServices;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productController")
public class ProductController {
	@Autowired
	private ProductServices productServices;
	
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProductHandler(@Valid @RequestBody Product product, @RequestParam String categoryName, @RequestParam String key) throws ProductException, CategoryException, AdminException, LoginException{

		Product newProduct = productServices.addProduct(product, categoryName, key);

		return new ResponseEntity<Product>(newProduct, HttpStatus.OK);

	}
	
	@PutMapping("/updateProduct")
	public ResponseEntity<Product> updateProductDetailsHandler(@Valid @RequestBody Product product, @RequestParam("key") String key) throws ProductException, AdminException, LoginException{

		Product updatedProduct = productServices.updateProduct(product, key);
		
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);

	}
	
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<Product> deleteProductHandler(@RequestParam("productId") Integer productId,@RequestParam("categoryName") String categoryName, @RequestParam String key) throws ProductException, AdminException, LoginException, CategoryException{

		Product deletedProduct = productServices.deleteProduct(productId, categoryName, key);

		return new ResponseEntity<Product>(deletedProduct, HttpStatus.OK);
	}
	
	@GetMapping("/productByProductName")
	public ResponseEntity<Product> findProductByProductNameHandler(@RequestParam("productName") String productName) throws ProductException {

		Product product = productServices.getProductByProductName(productName);

		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}
	
	@GetMapping("/productByCategoryName")
	public ResponseEntity<List<Product>> findProductByCategoryNameHandler(@RequestParam("categoryName") String categoryName) throws ProductException, CategoryException{

		List<Product> product = productServices.getProductsByCategory(categoryName);

		return new ResponseEntity<List<Product>>(product, HttpStatus.OK);

	}
	
	@GetMapping("/Products")
	public ResponseEntity<List<Product>> findAllProductHandler() throws ProductException{

		List<Product> product = productServices.getAllProducts();

		return new ResponseEntity<List<Product>>(product, HttpStatus.OK);

	}
}