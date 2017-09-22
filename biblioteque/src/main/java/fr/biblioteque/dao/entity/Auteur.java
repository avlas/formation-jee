package fr.biblioteque.dao.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="auteur")
@NamedQuery(name="auteur.findByLangue", query="select a from Auteur a where a.langue=:langue")
public class Auteur {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="prenom")
	private String prenom;
	
	@Column(name="langue")
	private String langue;
	
	@OneToMany(mappedBy="auteur")
	private Set<Livre> livres;


	public Auteur() {
	}

	
	/**
	 * @param nom
	 * @param prenom
	 * @param langue
	 * @param livres
	 */
	public Auteur(String nom, String prenom, String langue, Set<Livre> livres) {
		this.nom = nom;
		this.prenom = prenom;
		this.langue = langue;
		this.livres = livres;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public Set<Livre> getLivres() {
		return livres;
	}

	public void setLivres(Set<Livre> livres) {
		this.livres = livres;
	}

	@Override
	public String toString() {
		return "Auteur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", langue=" + langue + ", livres=" + livres
				+ "]";
	}
		
}
