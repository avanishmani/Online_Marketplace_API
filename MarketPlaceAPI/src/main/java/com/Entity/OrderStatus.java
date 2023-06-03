package com.Entity;

public enum OrderStatus {
	Delivered("Delivered"), Shipped("Shipment has been released for the destination "),
	NotShipped("Shipment is pending");

	private String orderStatus;

	private OrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
}
