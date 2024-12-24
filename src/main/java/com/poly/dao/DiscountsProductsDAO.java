package com.poly.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.DiscountsProducts;

public interface DiscountsProductsDAO extends JpaRepository<DiscountsProducts, String> {

	@Query("SELECT dp FROM DiscountsProducts dp WHERE dp.discountsProductsId = :id")
    Optional<DiscountsProducts> getDiscountsProductsById(@Param("id") String id);
	
	@Query("SELECT dp FROM DiscountsProducts dp " +
	        "WHERE dp.dateStart <= GETDATE() AND dp.dateEnd >= GETDATE()")
	List<DiscountsProducts> getDiscountProductWithCurrentDate();

}
