package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Colors;

public interface ColorsDAO extends JpaRepository<Colors, Integer> {
	@Query(value = "select colorname "
			+ "from products,images,colors "
			+ "where products.productsid = images.productsid and images.colorid = colors.colorid and products.categorizesid = :id "
			+ "group by  colorname",nativeQuery = true)
	List<Object[]> fillAllColor(@Param("id") int id);
}
