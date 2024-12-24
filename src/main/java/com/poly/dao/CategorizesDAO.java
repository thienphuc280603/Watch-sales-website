package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Categorizes;

public interface CategorizesDAO extends JpaRepository<Categorizes, Integer> {
	@Query(value = "select categorizes.name, categorizes.categorizesid "
			+ "from categorizes, products "
			+ "where categorizes.categorizesid = products.categorizesid  "
			+ "group by categorizes.name, categorizes.categorizesid",nativeQuery = true)
	List<Object[]> fillAllcate();

	@Query(value = "select * from categorizes", nativeQuery = true)
	List<Categorizes> findAllCategory();
}
