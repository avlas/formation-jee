package fr.biblioteque.business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.biblioteque.dao.EntityManagerInstance;
import fr.biblioteque.dao.entity.Livre;

public class LivreServiceImpl implements LivreService {

	private EntityManager em;

	public LivreServiceImpl() {
		this.em = EntityManagerInstance.getInstance();
	}

	public List<Livre> findAll() {
		TypedQuery<Livre> query = em.createQuery("from Livre", Livre.class);
		return query.getResultList();
	}

	public Livre findById(Integer id) {
		Livre livre = em.find(Livre.class, id);
		return livre;
	}

	public void insert(Livre livre) {
		this.em.getTransaction().begin();
		
		em.persist(livre);	
		
		this.em.getTransaction().commit();
	}

	public void update(Livre livre) {
		this.em.getTransaction().begin();
		
		em.merge(livre);	
		
		this.em.getTransaction().commit();
		
/*		Livre livre = em.find(Livre.class, livreTpUpdate.getId());

		if (livre != null) {
			livre.setTitre(livreTpUpdate.getTitre());
			livre.setDatePublication(livreTpUpdate.getDatePublication());
			livre.setDescription(livreTpUpdate.getDescription());
			livre.setCategorie(livreTpUpdate.getCategorie());
			livre.setAuteur(livreTpUpdate.getAuteur());
			livre.setExemplaires(livreTpUpdate.getExemplaires());
			livre.setExemplairesDispo(livreTpUpdate.getExemplairesDispo());
		}	*/
	}
	
	public void delete(Livre livre) {		
		this.em.getTransaction().begin();	
		
		em.remove(livre);	
		
		this.em.getTransaction().commit();
	}
}
