package com.poly.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DiscountUserDAO;
import com.poly.entity.DiscountUser;

@Service
public class DiscountUserImpl {
	@Autowired
	DiscountUserDAO discountUserDAO;
	
	
	public DiscountUser saveDiscountUser(DiscountUser discount) {
		return discountUserDAO.save(discount);
	}
}
