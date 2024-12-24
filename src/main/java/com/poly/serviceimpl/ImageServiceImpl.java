package com.poly.serviceimpl;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.ImageDAO;
import com.poly.entity.Images;
import com.poly.entity.Products;
import com.poly.entity.Sizes;
import com.poly.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	ImageDAO imageDAO;

	@Override
	public List<Images> getAllImage() {
		return imageDAO.findAll();
	}

	@Override
	public Images getImageById(Integer id) {
		return imageDAO.getImageById(id);
	}

	public Images updateImage(Images image) {
		Integer id = image.getImagesId();
		Images images = imageDAO.getImageById(id);
		images.setColor(image.getColor());
		images.setProducts(image.getProducts());
		images.setName(image.getName());
        return imageDAO.save(images);

	}
	
	public void addImage(Images images) {
        imageDAO.save(images);
    }
	
	public void deleteImage(Images images) {
		Integer id = images.getImagesId();
		Images image = imageDAO.getImageById(id);
		imageDAO.delete(image);
	}

	@Override
	public Page<Images> getAllImages(Pageable page) {
		return imageDAO.findAll(page);
	}
	
	@Override
	public List<Object[]> allImage(int id) {
		List<Object[]> list = imageDAO.allImage(id);
		return list;
	}
	
	public List<Images> getImageByProductId(Integer id) {
		return imageDAO.getAllImageByProductId(id);
	}
}
