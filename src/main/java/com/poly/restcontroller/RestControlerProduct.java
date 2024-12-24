package com.poly.restcontroller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.dao.ProductsDAO;
import com.poly.entity.Products;
import com.poly.serviceimpl.ProductServiceImpl;



@CrossOrigin("*")
@RestController
public class RestControlerProduct {
	@Autowired
	ProductsDAO proDao;
	@Autowired
	ProductServiceImpl proService;
	  @GetMapping("/getProductId/{id}")
	    public Object[] getproductId(@PathVariable("id") int id) {
	            
	        return  proDao.getproduct(id);
	    }
	 
	@GetMapping("/rest/product/{id}")
	public Object[] getOne(@PathVariable("id") int id){
		return proDao.detailSpTuan(id);
	}
	
	@PutMapping("/api/putProduct")
	public Products putProduct(@RequestBody JsonNode quantityData) {
	     return proService.update(quantityData);
	}

	

}
