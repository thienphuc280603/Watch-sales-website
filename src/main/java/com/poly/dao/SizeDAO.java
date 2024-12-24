package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Sizes;

public interface SizeDAO extends JpaRepository<Sizes, Integer> {
	Page<Sizes> findAll(Pageable pageable);

	@Query(value = "select * from sizes where productsid = ?1", nativeQuery = true)
	List<Sizes> getAllSizeByProductId(Integer id);

	@Query(value = "select * from sizes where sizeid = ?1", nativeQuery = true)
	Sizes getSizeById(Integer id);

	@Query(value = "select size " + "from sizes " + "where sizes.categorizesid= :id " + "group by size "
			+ "ORDER BY size ", nativeQuery = true)
	List<Object[]> listsize(@Param("id") int id);

	
	@Query(value = "select products.name , sizes.size, products.productsid " + "from products,categorizes,sizes "
			+ "where products.categorizesid = categorizes.categorizesid and sizes.productsid = products.productsid and categorizes.categorizesid = :id and sizes.size like :size"
			+ " group by products.name , sizes.size, products.productsid", nativeQuery = true)
	List<Object[]> listfilterSize(@Param("id") int id, @Param("size") String size);

}
