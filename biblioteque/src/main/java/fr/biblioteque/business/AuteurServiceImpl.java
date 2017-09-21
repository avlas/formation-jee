package fr.biblioteque.business;

import java.util.List;

import javax.persistence.TypedQuery;

import fr.biblioteque.dao.EntityManagerInstance;
import fr.biblioteque.dao.entity.Auteur;

public class AuteurServiceImpl implements AuteurService {
	
	public AuteurServiceImpl() {
	}

	public List<Auteur> findAll() {
		 TypedQuery<Auteur> query = EntityManagerInstance.getInstance().createQuery("from Auteur", Auteur.class); 
	     return query.getResultList();
	}

	public Auteur findById(Integer id) {		
		Auteur auteur = EntityManagerInstance.getInstance().find(Auteur.class, id);
		return auteur;
	}

	public void insert(Auteur auteur) {
		EntityManagerInstance.getInstance().getTransaction().begin();
		
		EntityManagerInstance.getInstance().persist(auteur);	

		EntityManagerInstance.getInstance().getTransaction().commit();
	}

	public void update(Auteur auteur) {
		EntityManagerInstance.getInstance().getTransaction().begin();
		
		EntityManagerInstance.getInstance().merge(auteur);	
		
		EntityManagerInstance.getInstance().getTransaction().commit();
	}	
	
	public void delete(Auteur auteur) {		
		EntityManagerInstance.getInstance().getTransaction().begin();	
		
		EntityManagerInstance.getInstance().remove(auteur);	
		
		EntityManagerInstance.getInstance().getTransaction().commit();
	}	
}
