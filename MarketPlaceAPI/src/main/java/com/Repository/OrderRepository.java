package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
