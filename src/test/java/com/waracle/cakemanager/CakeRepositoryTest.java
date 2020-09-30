package com.waracle.cakemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.waracle.cakemanager.entities.Cake;
import com.waracle.cakemanager.entities.CakeManager;
import com.waracle.cakemanager.repositories.CakeManagerRepository;
import com.waracle.cakemanager.repositories.CakeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CakeRepositoryTest {
    
	@Autowired
	CakeManagerRepository repository;
	
	@Autowired
	CakeRepository cakeRepository;
	
	
    @Test
    public void testSaveCake() {
    	CakeManager sliver = this.repository.save(new CakeManager("sliver", "gierke", "ROLE_MANAGER"));
    	Cake cake =  new Cake("Sample", "cake", "test data", sliver);
    	Cake cake2= cakeRepository.save(cake);
		
		assertNotNull(cake2);
        assertEquals(cake2.getFirstName(), cake.getFirstName()); 
        assertEquals(cake2.getLastName(), cake.getLastName());
    	
    }
    
    @Test
    public void testGetCake() {
    	CakeManager greg = this.repository.save(new CakeManager("gregfgdg", "turnquistsdfb", "ROLE_MANGER"));
        Cake cake = new Cake("admin", "admin", "admin@gmail.com",greg);
        Cake Cake2=cakeRepository.save(cake);
        assertNotNull(Cake2);
        assertEquals(Cake2.getFirstName(), cake.getFirstName());
        assertEquals(Cake2.getLastName(), cake.getLastName());
    }

    @Test
    public void testDeleteCake() {
    	CakeManager greg = this.repository.save(new CakeManager("greg", "turnquistsdfb", "ROLE_"));
        Cake cake = new Cake("admin", "admin", "admin@gmail.com",greg);
        cakeRepository.save(cake);
        cakeRepository.delete(cake);
    }

    @Test
    public void findAllCakes() {
    	CakeManager greg = this.repository.save(new CakeManager("greg", "turnquistsdfb", "ROLE_MANAGER"));
        Cake cake = new Cake("admin", "admin", "admin@gmail.com",greg);
        cakeRepository.save(cake);
        assertNotNull(cakeRepository.findAll());
    }

    @Test
    public void deletByCakeIdTest() {
    	CakeManager greg = this.repository.save(new CakeManager("greg", "turnquistsdfb", "ROLE_MANAGER"));
        Cake cake = new Cake("admin", "admin", "admin@gmail.com",greg);
        Cake emp = cakeRepository.save(cake);
        cakeRepository.deleteById(emp.getId());
    }


	
	
	
	
}
