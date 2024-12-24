package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Images;

public interface ImageDAO extends JpaRepository<Images, Integer> {
	@Query(value = "select images.name "
			+ "from images, products "
			+ "where images.productsid = products.productsid and products.productsid= :id ", nativeQuery = true)
	List<Object[]> allImage(@Param("id") int id);
	
	@Query(value = "select * from images where imagesid = ?1", nativeQuery = true)
	Images getImageById(Integer id);
	@Query(value = "select * from images where productsid = ?1", nativeQuery = true)
	List<Images> getAllImageByProductId(Integer id);


	
}
