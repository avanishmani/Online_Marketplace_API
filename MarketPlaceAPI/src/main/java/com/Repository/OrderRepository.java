package com.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("from Order where customer.customerId=:idcustomer")
	public List<Order> findBycustomerId(@Param("idcustomer") Integer idcardid);
	
	public List<Order> findByOrderDate(LocalDate orderDate);
}
