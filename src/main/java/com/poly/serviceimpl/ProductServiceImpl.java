package com.poly.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.ProductsDAO;
import com.poly.entity.Order;
import com.poly.entity.Products;
import com.poly.entity.User;
import com.poly.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	private final ProductsDAO productsDAO;

    @Autowired
    public ProductServiceImpl(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Override
    public List<Object[]> listVongtay() {
        return productsDAO.listVongtay();
    }

    @Override
    public List<Object[]> listNhan() {
        return productsDAO.listNhan();
    }

    @Override
    public List<Object[]> listDh() {
        return productsDAO.listDh();
    }

    @Override
    public List<Object[]> listAllSp(int id) {
        return productsDAO.listAllSp(id);
    }

    @Override
    public List<Object[]> detailSp(int id) {
        return productsDAO.detailSp(id);
    }


	@Override
	public Object getProductByIdAdmin(int id) {
		return productsDAO.getProductByIdAdmin(id);
	}

	public List<Products> getAllProduct(){
		return productsDAO.findAll();
	}
	

	public Products getProductByID(Integer id){
		return productsDAO.getProductByID(id);
	}
	
	public void addProduct(Products newProduct) {
        productsDAO.save(newProduct);
    }
	
	
	public Products updateProduct(Products pro) {
		Integer id = pro.getProductsId();
		Products products = productsDAO.getProductByID(id);
		products.setImage(pro.getImage());
		products.setName(pro.getName());
		products.setPrice(pro.getPrice());
		products.setQuantity(pro.getQuantity());
		products.setCategorizes(pro.getCategorizes());
		products.setBrand(pro.getBrand());
		products.setGender(pro.getGender());
		products.setDescribe(pro.getDescribe());
		products.setActive(pro.isActive());
		products.setDiscountsProducts(pro.getDiscountsProducts());
            return productsDAO.save(products);

	}

	@Override
	public List<Products> getProductByProductName(String productName) {
		return productsDAO.getProductByProductName(productName);
	}

	
	
	
	
	
	
	@Override
	public List<Object[]> listProduct() {
		List<Object[]> listProduct = productsDAO.listsp();
		return listProduct;
	}

	@Override
	public List<Object[]> listAllProduct(int id) {
		List<Object[]> listAllProducts = productsDAO.listAllSp(id);
		return listAllProducts;
	}

	@Override
	public List<Object[]> detailProduct(int id) {
		List<Object[]> detail = productsDAO.detailSp(id);
		return detail;
	}

	@Override
	public List<Object[]> listProductByType(String type) {
		List<Object[]> list = productsDAO.listProductByType(type);
		return list;
	}

	@Override
	public List<Object[]> sameProducts(String type) {
		List<Object[]> list = productsDAO.listProductByType(type);
		return list;
	}
	
	public List<Object[]> getBrandId(int id) {
		List<Object[]> list = productsDAO.getBrandId(id);
		return list;
	}

	@Override
	public List<Object[]> findProduct(String name) {
		List<Object[]> list = productsDAO.findproduct(name);
		return list;
	}

	@Override
	public Object[] detailSpTuan(int id) {
		
		return productsDAO.detailSpTuan(id);
	}
	
	public List<Products> getProductByKeyWord(String keyword){
		return  productsDAO.getProductByKeyWord(keyword);

	}

	@Override
	public Products update(JsonNode quantityData) {
		 ObjectMapper mapper = new ObjectMapper();
		 Products product = mapper.convertValue(quantityData, Products.class);
		 productsDAO.save(product);
		return product;
	}

	@Override
	public List<Object[]> listTrending() {
		List<Object[]> list = productsDAO.listTrending();
		return list;
	}

	@Override
	public List<Object[]> listGG() {
		List<Object[]> list = productsDAO.listGG();
		return list;
	}
}
