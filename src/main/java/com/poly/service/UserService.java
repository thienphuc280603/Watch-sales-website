package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.poly.entity.User;

public interface UserService {
	User findByUserId(String userId);

	User findByEmail(String email);

	Page<User> findUsersWithPagination(int page, int size);

	User saveUser(User user);

	void deleteUser(String userId);

	User updateUser(User updatedUser);
	
	////////////////
	

	


    Page<User> getUsersByRole(String role, int page, int size);

    List<User> findListByRole(@Param("role") String role);
    List<User> listall();
}
