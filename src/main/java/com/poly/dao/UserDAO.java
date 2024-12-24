package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.User;


public interface UserDAO extends JpaRepository<User, String> {
	User findByUserId(String userId);
    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    void deleteByUserId(String userId);
    
    
    @Query("SELECT u FROM User u WHERE u.userId = :id")
	User findByUserIdTuan(@Param("id") String id);
    
    /////////////



    @Query("SELECT u FROM User u WHERE u.role = :role")
    Page<User> findAllByRole(@Param("role") String role, Pageable pageable);


    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findListByRole(@Param("role") String role);
    


    
 
}
