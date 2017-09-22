package fr.biblioteque.business;

import java.util.List;

import fr.biblioteque.dao.entity.Auteur;

public interface GenericService<T> {
	
	List<T> findAll(String queryString, Class<T> entityClass);
	
	T findById(Class<T> entityClass, Integer id);	
	
	void insert(T t);
	
	void update(T t);
	
	void delete(T t);
	
	List<Auteur> findByLangue(String langue);
}
