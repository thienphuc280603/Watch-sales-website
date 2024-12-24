package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Brands;

public interface BrandsDAO extends JpaRepository<Brands, Integer> {
	@Query(value = "select brands.name "
			+ "from brands, products "
			+ "where brands.brandid= products.brandid and products.categorizesid = :id "
			+ "group by brands.name",nativeQuery = true)
	List<Object[]> listAllBrand(@Param("id") int id);
}
