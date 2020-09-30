package com.waracle.cakemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.waracle.cakemanager.entities.Cake;
import com.waracle.cakemanager.entities.CakeManager;
import com.waracle.cakemanager.repositories.CakeManagerRepository;
import com.waracle.cakemanager.repositories.CakeRepository;

@Component
public class DataBaseLoader implements CommandLineRunner {

	private final CakeRepository repository;

	private final CakeManagerRepository managers;

	@Autowired
	public DataBaseLoader(CakeRepository repository, CakeManagerRepository managers) {
		this.repository = repository;
		this.managers = managers;
	}
	@Override
	public void run(String... strings) throws Exception {
		CakeManager greg = this.managers.save(new CakeManager("greg", "password", "ROLE_MANAGER"));
		CakeManager oliver = this.managers.save(new CakeManager("oliver", "password", "ROLE_MANAGER"));

	SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("greg", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));
	this.repository.save(new Cake("Caramel Apple", "Cake", "Made from Caramel and Apple", greg));
	this.repository.save(new Cake("Orange Velvet", "Cake", "Made from fresh sweet oranges", greg));
	this.repository.save(new Cake("Pineapple and Rum", "Cake", "Made from Farm picked pineapples", greg));

	SecurityContextHolder.getContext().setAuthentication(
		new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
			AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

	this.repository.save(new Cake("Raspberry & Ricotta Cheese", "Cake", "Made from Raspberry and Ricotta cheese", oliver));
	this.repository.save(new Cake("Lemon Ginger", "Cake", "Made from Juicy lemons", oliver));
	this.repository.save(new Cake("Apricot crumble", "Cake", "Made from True Apricots", oliver));

	SecurityContextHolder.clearContext();
//
//	
//	public void run(String... strings) throws Exception {
//		this.repository.save(new CakeManager("Mango", "Cake", "This cake is prepared using the mango"));
//	}
}
}
