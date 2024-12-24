package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Item;

public interface ItemDAO extends JpaRepository<Item, Integer> {

	@Query("SELECT i FROM Item i WHERE i.order.orderId = :orderId")
	List<Item> findItemsByOrderId(@Param("orderId") Integer orderId);
	
	@Query(value = "select orders.ordersid , products.image, products.name,items.quantity,items.price "
			+ "from items, orders,products "
			+ "where items.ordersid = orders.ordersid and items.productsid = products.productsid" , nativeQuery = true)
	List<Object[]> listItemForOder();
	
	@Query(value = "select orders.ordersid, sum(items.quantity) as 'quantity', sum(items.price) as 'price' "
			+ "from items, orders,products "
			+ "where items.ordersid = orders.ordersid and items.productsid = products.productsid "
			+ "group by orders.ordersid",nativeQuery = true)
	List<Object[]> listSumPrice();
	
}
