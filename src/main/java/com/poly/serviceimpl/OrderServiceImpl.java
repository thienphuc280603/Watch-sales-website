package com.poly.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.ItemDAO;
import com.poly.dao.OrderDAO;
import com.poly.entity.Item;
import com.poly.entity.Order;
import com.poly.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	ItemDAO itemDAO;
	
	public final OrderDAO orderDAO;
	
	public OrderServiceImpl(OrderDAO orderDAO) {
		super();
		this.orderDAO = orderDAO;
	}

	@Override
	public Order findByOrderId(Integer id) {
		return orderDAO.findByOrderId(id);
	}

	@Override
	public List<Order> findAll() {
		return orderDAO.findAll();
	}

	@Override
	public Order saveOrder(Order order) {
		 return orderDAO.save(order);
	}
	
	@Override
	public Order create(JsonNode orderData) {
	    ObjectMapper mapper = new ObjectMapper();

	    // Chuyển đổi dữ liệu JSON thành đối tượng Order
	    Order order = mapper.convertValue(orderData, Order.class);
	    
	    // Lưu đối tượng Order vào cơ sở dữ liệu
	    orderDAO.save(order);

	    // Chuyển đổi danh sách các mục từ dữ liệu JSON và lưu chúng vào cơ sở dữ liệu
	    TypeReference<List<Item>> type = new TypeReference<List<Item>>() {};
	    List<Item> details = mapper.convertValue(orderData.get("items"), type)
	            .stream()
	            .peek(d -> d.setOrder(order))
	            .collect(Collectors.toList());

	    itemDAO.saveAll(details);
	    
	    // Trả về đối tượng Order sau khi đã lưu vào cơ sở dữ liệu
	    return order;
	}
	
	@Override
	public List<Object[]> getThongke(String startDate, String endDate) {
		return orderDAO.getThongke(startDate, endDate);
	}

	@Override
	public List<Object[]> getThongkeTheoUser(String startDate, String endDate) {
		return orderDAO.getThongkeTheoUser(startDate, endDate);
	}

	@Override
	public java.util.List<Object[]> getAllThongke() {
		return orderDAO.getAllThongke();
	}

	@Override
	public List<Object[]> getAllThongkeTheoUser() {
		return orderDAO.getAllThongkeTheoUser();
	}

	@Override
	public Double getTotalPriceForMonthAndYear(int month, int year) {
		return orderDAO.getTotalPriceForMonthAndYear(month, year);
	}

	@Override
	public List<Object[]> fillAllForUser(String id) {
		List<Object[]> list = orderDAO.findAllForUser(id);
		return list;
	}

	//bill
	@Override
	public List<Order> findByActiveAndStatus(boolean active, boolean status) {
		return orderDAO.findByActiveAndStatus(active, status);
	}


}
