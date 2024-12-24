package com.poly.restcontroller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.DiscountUserDAO;
import com.poly.entity.DiscountUser;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DiscountsUserRestController {
	@Autowired
	DiscountUserDAO disDao;
	
	@GetMapping("/discountUser")
	public DiscountUser getDiscountUser(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userid");
	    DiscountUser discountUser = disDao.findByUserId(userId);
	    discountUser.getDiscountAmount();
	    return discountUser;
	   
	}
	
	@DeleteMapping("/deleteDiscountUser/{id}")
	public void deleteDiscountUser(@PathVariable("id") String id) {
		disDao.deleteById(id);
	}
}
