package com.waracle.cakemanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.waracle.cakemanager.entities.Cake;
import com.waracle.cakemanager.entities.CakeManager;
import com.waracle.cakemanager.repositories.CakeManagerRepository;

@Component
@RepositoryEventHandler(Cake.class) 
public class SpringDataRestEventHandler {

	private final CakeManagerRepository managerRepository;

	@Autowired
	public SpringDataRestEventHandler(CakeManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void applyUserInformationUsingSecurityContext(Cake cake) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		CakeManager manager = this.managerRepository.findByName(name);
		if (manager == null) {
			CakeManager newManager = new CakeManager();
			newManager.setName(name);
			newManager.setRoles(new String[]{"ROLE_MANAGER"});
			manager = this.managerRepository.save(newManager);
		}
		cake.setManager(manager);
	}
}
