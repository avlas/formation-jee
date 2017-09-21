package fr.biblioteque.business;

import java.util.List;

import fr.biblioteque.dao.entity.Auteur;

public interface AuteurService {
	
	List<Auteur> findAll();	
	
	Auteur findById(Integer id);
	
	void insert(Auteur auteur);
	
	void update(Auteur auteur);	
	
	void delete(Auteur auteur);	
}
