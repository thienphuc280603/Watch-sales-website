package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.ColorsDAO;
import com.poly.entity.Colors;
import com.poly.service.ColorService;
@Service
public class ColorServiceImpl implements ColorService{
	@Autowired
	ColorsDAO colorsDAO;

	@Override
	public List<Colors> getAllColorImage() {
		return colorsDAO.findAll();
	}

	@Override
	public List<Object[]> listAllColor(int id) {
		return colorsDAO.fillAllColor(id);
	}
	


}
