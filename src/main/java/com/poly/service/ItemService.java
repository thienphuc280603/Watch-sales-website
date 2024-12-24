package com.poly.service;

import java.util.List;

import com.poly.entity.Item;

public interface ItemService {
	List<Item> findAllItemByOrder(Integer orderId);

	Item saveItem(Item item);
	
	List<Object[]> listItemForOder();
	List<Object[]> listSumPrice();
}
