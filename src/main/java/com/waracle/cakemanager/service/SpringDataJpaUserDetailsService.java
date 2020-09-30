package com.waracle.cakemanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.waracle.cakemanager.entities.CakeManager;
import com.waracle.cakemanager.repositories.CakeManagerRepository;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

	private final CakeManagerRepository repository;

	@Autowired
	public SpringDataJpaUserDetailsService(CakeManagerRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		CakeManager manager = this.repository.findByName(name);
		
		return new User(manager.getName(), manager.getPassword(),
				true, true, true, true, AuthorityUtils.createAuthorityList(manager.getRoles()));
	}

}
