package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategorizesDAO;
import com.poly.entity.Categorizes;
import com.poly.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategorizesDAO categorizesDAO;
	
	
	public List<Categorizes> getAllCategory() {
		return categorizesDAO.findAllCategory();
	}

	@Override
	public List<Object[]> listcate() {
		List<Object[]> list =categorizesDAO.fillAllcate();
		return list;
	}

}
