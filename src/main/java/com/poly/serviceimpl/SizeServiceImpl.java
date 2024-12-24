package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.SizeDAO;
import com.poly.entity.Images;
import com.poly.entity.Products;
import com.poly.entity.Sizes;
import com.poly.service.SizeService;

@Service
public class SizeServiceImpl implements SizeService{
	
	@Autowired
	SizeDAO sizeDAO;

	@Override
	public List<Sizes> getAllSizeByProductId(Integer id) {
		return sizeDAO.getAllSizeByProductId(id);
	}
	
	public List<Sizes> getAllSizes(){
		return sizeDAO.findAll();
	}

	public Sizes getSizeById(Integer id) {
		return sizeDAO.getSizeById(id);
	}
	
	public Sizes updateSize(Sizes size) {
		Integer id = size.getSizeId();
		Sizes sizes = sizeDAO.getSizeById(id);
		sizes.setSize(size.getSize());
		sizes.setCategorizes(sizes.getCategorizes());
		sizes.setProduct(sizes.getProduct());
        return sizeDAO.save(sizes);

	}
	
	 public void deleteSize(Integer sizeId) {
	        sizeDAO.deleteById(sizeId);
	    }

	@Override
	public Page<Sizes> findAll(Pageable pageable) {
		return sizeDAO.findAll(pageable);
	}
	
	public void addSize(Sizes size) {
        sizeDAO.save(size);
    }
	
	@Override
	public List<Object[]> listsize(int id) {
		List<Object[]> listsizes = sizeDAO.listsize(id);
		return listsizes;
	}

	@Override
	public List<Object[]> listfilterSize(int id, String size) {
		List<Object[]> listfilterSizes = sizeDAO.listfilterSize(id, size);
		return listfilterSizes;
	}


	

}
