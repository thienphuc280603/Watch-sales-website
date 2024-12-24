package com.poly.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.entity.Images;

public interface ImageService {
	List<Images> getAllImage();
	
	Images getImageById(Integer id);
	
	Page<Images> getAllImages(Pageable page);
	
	List<Object[]> allImage(int id);
}
