package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Sizes;

public interface SizeService {
	List<Sizes> getAllSizeByProductId(Integer id);
	
	Page<Sizes> findAll(Pageable pageable);
	
	List<Object[]> listsize (int id);
	List<Object[]> listfilterSize(int id, String size);
	

	

}
