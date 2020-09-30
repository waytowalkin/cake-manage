package com.waracle.cakemanager.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import com.waracle.cakemanager.entities.Cake;

@PreAuthorize("hasRole('ROLE_MANAGER')") 
public interface CakeRepository extends PagingAndSortingRepository<Cake, Long> {

	@Override
	@PreAuthorize("#Cake?.manager == null or #Cake?.manager?.name == authentication?.name")
	Cake save(@Param("Cake") Cake cake);

	@Override
	@PreAuthorize("@CakeRepository.findById(#id)?.manager?.name == authentication?.name")
	void deleteById(@Param("id") Long id);

	@Override
	@PreAuthorize("#Cake?.manager?.name == authentication?.name")
	void delete(@Param("Cake") Cake cake);

	

}
