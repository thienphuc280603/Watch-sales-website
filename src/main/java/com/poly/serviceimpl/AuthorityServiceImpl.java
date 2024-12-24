package com.poly.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.AuthorityDAO;
import com.poly.entity.Authority;
import com.poly.service.AuthorityService;
@Service
public class AuthorityServiceImpl implements AuthorityService{
	
	private final AuthorityDAO authorityDAO;

    @Autowired
    public AuthorityServiceImpl(AuthorityDAO authorityDAO) {
        this.authorityDAO = authorityDAO;
    }

	@Override
	public List<Authority> getAllAuthorities() {
		return authorityDAO.findAll();
	}

	@Override
	public Optional<Authority> getAuthorityById(int id) {
		 return authorityDAO.findById(id);
	}

	@Override
	public void saveAuthority(Authority authority) {
		authorityDAO.save(authority);	
	}

	@Override
	public void deleteAuthority(int id) {
		authorityDAO.deleteById(id);		
	}

	@Override
	public List<Authority> getListAuthorityByRoleId(int roleId) {
		return authorityDAO.findAuthoritiesByRoleId(roleId);
	}

}
