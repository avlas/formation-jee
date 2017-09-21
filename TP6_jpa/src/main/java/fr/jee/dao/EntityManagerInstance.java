package fr.jee.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerInstance {
	
	public static EntityManager getInstance() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("restaurant");
		return entityManagerFactory.createEntityManager();
	}	
}
