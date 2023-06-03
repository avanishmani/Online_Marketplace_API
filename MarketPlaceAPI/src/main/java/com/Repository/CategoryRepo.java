package com.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	public Category findByCategoryName(String categoryName);
}
