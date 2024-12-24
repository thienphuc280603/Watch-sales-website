package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Genders;


public interface GendersDAO extends JpaRepository<Genders, Integer> {
	@Query(value = "select * \r\n"
			+ "from genders", nativeQuery = true)
	List<Genders> getAllGenders();
	
	@Query(value = "select g.gender\r\n"
			+ "from genders g, products p\r\n"
			+ "where g.genderid = p.genderid and p.productsid = ?1", nativeQuery = true)
	Genders getGenderByProductID(Integer id);
}
