package fr.biblioteque.business;

import java.util.List;

import javax.persistence.TypedQuery;

import fr.biblioteque.dao.EntityManagerInstance;
import fr.biblioteque.dao.entity.Auteur;

public class GenericServiceImpl<T> implements GenericService<T> {

	public GenericServiceImpl() {
	}

	public List<T> findAll(String queryString, Class<T> entityClass) {
		 TypedQuery<T> query = EntityManagerInstance.getInstance().createQuery(queryString, entityClass); 
	     return query.getResultList();
	}

	public T findById(Class<T> entityClass, Integer id) {
		T t = EntityManagerInstance.getInstance().find(entityClass, id);
		return t;
	}

	public List<Auteur> findByLangue(String langue) {
		return EntityManagerInstance.getInstance().createNamedQuery("auteur.findByLangue", Auteur.class).setParameter("langue", langue).getResultList();
	}
	
	public void insert(T t) {
		EntityManagerInstance.getInstance().getTransaction().begin();
		
		EntityManagerInstance.getInstance().persist(t);	
	
		EntityManagerInstance.getInstance().getTransaction().commit();
	}
	
	public void update(T t) {
		EntityManagerInstance.getInstance().getTransaction().begin();
		
		EntityManagerInstance.getInstance().merge(t);
		
		EntityManagerInstance.getInstance().getTransaction().commit();
	}
	
	public void delete(T t) {
		EntityManagerInstance.getInstance().getTransaction().begin();
		
		EntityManagerInstance.getInstance().remove(t);
		
		EntityManagerInstance.getInstance().getTransaction().commit();
	}
}
