package com.poly.service;

import java.util.List;

import com.poly.entity.Genders;

public interface GenderService {
	List<Genders> getAllGenders();
	Genders getGenderByProductID(Integer id);
	List<Genders> listGen ();
}
