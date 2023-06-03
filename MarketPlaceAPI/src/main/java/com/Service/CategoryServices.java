package com.Service;

import java.util.List;

import com.Entity.Category;
import com.Exception.AdminException;
import com.Exception.CategoryException;
import com.Exception.LoginException;

public interface CategoryServices {
	public Category addCategory(Category category, String key) throws CategoryException, AdminException, LoginException;

	public Category updateCategory(Category category, String key) throws CategoryException, AdminException, LoginException;

	public Category deleteCategory(String categoryName, String key) throws CategoryException, AdminException, LoginException;
	
	public Category getCategoryByCategoryName(String categoryName) throws CategoryException;
	
	public List<Category> getAllCategorys() throws CategoryException;
}
