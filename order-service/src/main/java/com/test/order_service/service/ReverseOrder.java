package com.test.order_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.order_service.dto.OrderEvent;
import com.test.order_service.entity.Order;
import com.test.order_service.entity.OrderRepository;

@Component
public class ReverseOrder {

	@Autowired
	private OrderRepository repository;

	@KafkaListener(topics = "reversed-orders", groupId = "orders-group")
	public void reverseOrder(String event) {
		System.out.println("Inside reverse order for order "+event);
		
		try {
			OrderEvent orderEvent = new ObjectMapper().readValue(event, OrderEvent.class);

			Optional<Order> order = repository.findById(orderEvent.getOrder().getOrderId());

			order.ifPresent(o -> {
				o.setStatus("FAILED");
				this.repository.save(o);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
