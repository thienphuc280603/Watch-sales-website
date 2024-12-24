package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.entity.Authority;

public interface AuthorityService {
	List<Authority> getAllAuthorities();

	Optional<Authority> getAuthorityById(int id);

	void saveAuthority(Authority authority);

	void deleteAuthority(int id);

	List<Authority> getListAuthorityByRoleId(int roleId);
}
