package fr.biblioteque.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerInstance {
	
	private static EntityManager em = null;
	
	private EntityManagerInstance() {
		
	}
	
	public static EntityManager getInstance() {
		if(em == null) {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("biblio");
			em = entityManagerFactory.createEntityManager();
		} 
		return em;
	}	
}
