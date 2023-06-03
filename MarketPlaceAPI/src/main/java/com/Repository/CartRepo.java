package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {

}
