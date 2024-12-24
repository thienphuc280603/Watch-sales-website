package com.poly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;
import com.poly.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class RestControlerOrder {

	@Autowired
	OrderService odService;
	
	@PostMapping
	public Order create(@RequestBody JsonNode orderData) {
		return odService.create(orderData);
	}
}
