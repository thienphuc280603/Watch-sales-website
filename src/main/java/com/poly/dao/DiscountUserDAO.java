package com.poly.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.DiscountUser;

public interface DiscountUserDAO extends JpaRepository<DiscountUser, String> {
	DiscountUser findByUserUserId(String userId);
	
	@Query("SELECT d FROM DiscountUser d WHERE d.user.userId LIKE :userId")
	DiscountUser findByUserId(@Param("userId") String userId);

}
