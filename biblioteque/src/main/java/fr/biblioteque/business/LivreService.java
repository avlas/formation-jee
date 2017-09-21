package fr.biblioteque.business;

import java.util.List;

import fr.biblioteque.dao.entity.Livre;

public interface LivreService {
	
	List<Livre> findAll();
	
	Livre findById(Integer id);
	
	void insert(Livre livre);

	void update(Livre livre);		
	
	void delete(Livre livre);	

}
