package com.poly.service;

import java.util.List;

import com.poly.entity.Categorizes;

public interface CategoryService {
	List<Categorizes> getAllCategory();
	
	List<Object[]> listcate();
}
