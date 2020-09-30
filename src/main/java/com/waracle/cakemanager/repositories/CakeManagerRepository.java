package com.waracle.cakemanager.repositories;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.waracle.cakemanager.entities.CakeManager;

@RepositoryRestResource(exported = false)
public interface CakeManagerRepository extends Repository<CakeManager, Long> {

	CakeManager save(CakeManager manager);

	CakeManager findByName(String name);

}
