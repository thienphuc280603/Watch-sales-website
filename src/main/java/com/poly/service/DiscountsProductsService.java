package com.poly.service;

import java.util.List;

import com.poly.entity.DiscountsProducts;

public interface DiscountsProductsService {
	List<DiscountsProducts> getAllDiscountsProducts();

	DiscountsProducts getDiscountsProductsById(String id);

	DiscountsProducts createDiscountsProducts(DiscountsProducts discountsProducts);

	DiscountsProducts updateDiscountsProducts(DiscountsProducts updatedDiscountsProducts, String id);

	void deleteDiscountsProducts(String id);
	
	List<DiscountsProducts> getDiscountProductWithCurrentDate();
}
