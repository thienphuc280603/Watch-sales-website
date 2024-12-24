package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer> {

    @Query("SELECT a FROM Authority a WHERE a.role.roleId = :roleId")
    List<Authority> findAuthoritiesByRoleId(@Param("roleId") int roleId);
}
