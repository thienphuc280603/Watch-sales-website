package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ItemDAO;
import com.poly.entity.Item;
import com.poly.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService{
	
	private final ItemDAO itemDAO;

    @Autowired
    public ItemServiceImpl(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

	@Override
	public List<Item> findAllItemByOrder(Integer orderId) {
		return itemDAO.findItemsByOrderId(orderId);
	}

	@Override
	public Item saveItem(Item item) {
		return itemDAO.save(item);
	}

	@Override
	public List<Object[]> listItemForOder() {
		List<Object[]> list = itemDAO.listItemForOder();
		return list;
	}

	@Override
	public List<Object[]> listSumPrice() {
		List<Object[]> list = itemDAO.listSumPrice();
		return list;
	}
	

}
