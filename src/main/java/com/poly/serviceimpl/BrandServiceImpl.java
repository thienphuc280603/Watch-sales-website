package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.BrandsDAO;
import com.poly.entity.Brands;
import com.poly.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
	@Autowired
	BrandsDAO brandsDAO;

	public List<Brands> getAllBrand() {
		return brandsDAO.findAll();
	}
	
	@Override
	public List<Object[]> listbrands(int id) {
		List<Object[]> list = brandsDAO.listAllBrand(id);
		return list;
	}
	
	
}
