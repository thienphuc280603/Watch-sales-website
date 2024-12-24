package com.poly.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.ProductsDAO;
import com.poly.dao.UserDAO;
import com.poly.entity.User;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RestControlerUsers {
	@Autowired
	UserDAO uDao;
	
	
	@GetMapping("/user")
	public User getUserFromSession(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userid");
	   
	    User user = uDao.findByUserIdTuan(userId);
	    user.getUserId();
	    user.getPhone();
	    return user;
	}

	
//	@GetMapping("/rest/pros")
//	public List<Products> getAll(){
//		return proDao.findAll();
//	}
}
