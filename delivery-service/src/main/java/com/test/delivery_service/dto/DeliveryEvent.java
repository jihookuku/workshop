package com.test.delivery_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryEvent {
	private String type;
	private CustomerOrder order;

}
