package com.poly.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Order;
import com.poly.entity.Products;
import com.poly.entity.Sizes;

public interface ProductService {
	List<Object[]> listVongtay();
    List<Object[]> listNhan();
    List<Object[]> listDh();
    List<Object[]> listAllSp(int id);
    List<Object[]> detailSp(int id);
    Object getProductByIdAdmin(int id);
    

    
    List<Products> getProductByProductName(String productName);
    
    List<Object[]> listProduct();
	List<Object[]> listAllProduct(int id);
	List<Object[]> detailProduct(int id);
	List<Object[]> listProductByType(String type);
	
	
	List<Object[]> sameProducts(String type);
	List<Object[]> getBrandId(int id);
	List<Object[]> findProduct(String name);
	Object[] detailSpTuan(int id);
	
	Products update(JsonNode quantityData);
	List<Object[]> listTrending();
	List<Object[]> listGG();
}
