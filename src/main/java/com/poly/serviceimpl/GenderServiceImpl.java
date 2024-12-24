package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.GendersDAO;
import com.poly.entity.Genders;
import com.poly.service.GenderService;

@Service
public class GenderServiceImpl implements GenderService{
	private final GendersDAO gendersDAO;

    @Autowired
    public GenderServiceImpl(GendersDAO gendersDAO) {
        this.gendersDAO = gendersDAO;
    }

	@Override
	public List<Genders> getAllGenders() {
		return gendersDAO.getAllGenders();
	}

	@Override
	public Genders getGenderByProductID(Integer id) {
		return gendersDAO.getGenderByProductID(id);
	}
	@Override
	public List<Genders> listGen() {
		List<Genders> listgens = gendersDAO.findAll();
		return listgens;
	}
	
	


	
	
}
