package com.Entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	@ElementCollection
	@JoinTable(name = "orderedProducts")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Map<String, Integer> orderedProducts = new HashMap<>();

	@NotNull(message = "Order status can't be null")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String orderStatus = OrderStatus.NotShipped.getOrderStatus();

	private LocalDate orderDate = LocalDate.now();

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDate deliveryDate;

	@OneToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Address deliveryAddress;

	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Customer customer;
}
