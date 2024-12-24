package com.poly.service;

import java.util.List;

import com.poly.entity.Colors;

public interface ColorService {
	List<Colors> getAllColorImage();
	
	List<Object[]> listAllColor(int id);
}
