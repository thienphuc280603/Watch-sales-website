package com.poly.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DiscountsProductsDAO;
import com.poly.entity.DiscountsProducts;
import com.poly.service.DiscountsProductsService;

@Service
public class DiscountsProductsServiceImpl implements DiscountsProductsService {

	private final DiscountsProductsDAO discountsProductsDAO;

	@Autowired
	    public DiscountsProductsServiceImpl(DiscountsProductsDAO discountsProductsDAO) {
	        this.discountsProductsDAO = discountsProductsDAO;
	    }

	@Override
	public List<DiscountsProducts> getAllDiscountsProducts() {
		return discountsProductsDAO.findAll();
	}

	@Override
	public DiscountsProducts getDiscountsProductsById(String id) {
		return discountsProductsDAO.getDiscountsProductsById(id).get();
	}

	@Override
	public DiscountsProducts createDiscountsProducts(DiscountsProducts discountsProducts) {
		List<DiscountsProducts> list = this.getAllDiscountsProducts();
		for (DiscountsProducts discounts : list) {
			if (discountsProducts.getDiscountsProductsId().equals(discounts.getDiscountsProductsId())) {
				return discountsProductsDAO.save(discounts);
			}
		}
		return discountsProductsDAO.save(discountsProducts);
	}

	@Override
	public DiscountsProducts updateDiscountsProducts(DiscountsProducts updatedDiscountsProducts, String id) {
		Optional<DiscountsProducts> existingDiscountsProducts = discountsProductsDAO.findById(id);
        if (existingDiscountsProducts.isPresent()) {
            DiscountsProducts oldDiscountsProducts = existingDiscountsProducts.get();
            oldDiscountsProducts.setDiscountAmount(updatedDiscountsProducts.getDiscountAmount());
            oldDiscountsProducts.setDateStart(updatedDiscountsProducts.getDateStart());
            oldDiscountsProducts.setDateEnd(updatedDiscountsProducts.getDateEnd());
            return discountsProductsDAO.save(oldDiscountsProducts);
        } else {
            return null;
        }
	}

	@Override
	public void deleteDiscountsProducts(String id) {
		discountsProductsDAO.deleteById(id);
	}

	@Override
	public List<DiscountsProducts> getDiscountProductWithCurrentDate() {
		// TODO Auto-generated method stub
		return null;
	}

}