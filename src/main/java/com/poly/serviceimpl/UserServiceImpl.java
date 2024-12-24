package com.poly.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.UserDAO;
import com.poly.entity.User;
import com.poly.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDAO userDAO;

	@Override
	public User findByUserId(String userId) {
		return userDAO.findByUserId(userId);
	}

	@Override
	public User findByEmail(String email) {
		  return userDAO.findByEmail(email);
	}

	@Override
	public Page<User> findUsersWithPagination(int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
        return userDAO.findAll(pageable);
	}

	@Override
	public User saveUser(User user) {
		return userDAO.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		userDAO.deleteByUserId(userId);
	}

	@Override
	public User updateUser(User updatedUser) {
		String idUser = updatedUser.getUserId();
        User existingUser = userDAO.findByUserId(idUser);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setGender(updatedUser.isGender());
            return userDAO.save(existingUser);
        }
        return null;
	}
	
	
	//CODE DAN
	// Kiểm tra xem email đã tồn tại hay chưa
    public boolean isEmailExists(String email) {
        User user = userDAO.findByEmail(email);
        return user != null;
    }

    // Lưu thông tin người dùng vào database
    public void saveUserDan(User user) {
    	userDAO.save(user);
    }
    
    @Override
	public Page<User> getUsersByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
		return userDAO.findAllByRole(role, pageable);
	}

	@Override
	public List<User> findListByRole(String role) {
		return userDAO.findListByRole(role);
	}

	@Override
	public List<User> listall() {
		List<User> list = userDAO.findAll();
		return list;
	}

}
