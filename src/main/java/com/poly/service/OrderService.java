package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;

public interface OrderService {
	Order findByOrderId(Integer id);

	List<Order> findAll();

	List<Order> findByActiveAndStatus(@Param("active") boolean active, @Param("status") boolean status);

	Order saveOrder(Order order);

	Order create(JsonNode orderData);

	List<Object[]> getThongke(@Param("startDate") String startDate, @Param("endDate") String endDate);

	List<Object[]> getAllThongke();

	List<Object[]> getThongkeTheoUser(@Param("startDate") String startDate, @Param("endDate") String endDate);

	List<Object[]> getAllThongkeTheoUser();

	Double getTotalPriceForMonthAndYear(@Param("month") int month, @Param("year") int year);

	List<Object[]> fillAllForUser(String id);

}
